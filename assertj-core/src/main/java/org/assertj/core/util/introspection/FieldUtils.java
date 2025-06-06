/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2025 the original author or authors.
 */
package org.assertj.core.util.introspection;

import static java.lang.reflect.Modifier.isStatic;
import static org.assertj.core.util.Preconditions.checkArgument;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Shameless copy from Apache commons lang and then modified to keep only the interesting stuff for AssertJ.
 *
 * Utilities for working with fields by reflection. Adapted and refactored from the dormant [reflect] Commons sandbox
 * component.
 * <p>
 * The ability is provided to break the scoping restrictions coded by the programmer. This can allow fields to be
 * changed that shouldn't be. This facility should be used with care.
 */
class FieldUtils {

  // use ConcurrentHashMap as FieldUtils can be used in a multi-thread context
  private static final Map<Class<?>, Map<String, Field>> fieldsPerClass = new ConcurrentHashMap<>();

  /**
   * Gets an accessible <code>Field</code> by name breaking scope if requested. Superclasses/interfaces will be
   * considered.
   *
   * @param cls the class to reflect, must not be null
   * @param fieldName the field name to obtain
   * @param forceAccess whether to break scope restrictions using the <code>setAccessible</code> method.
   *          <code>False</code> will only match public fields.
   * @return the Field object
   * @throws IllegalArgumentException if the class or field name is null
   * @throws IllegalAccessException if field exists but is not public
   */
  static Field getField(final Class<?> cls, String fieldName, boolean forceAccess) throws IllegalAccessException {
    checkArgument(cls != null, "The class must not be null");
    checkArgument(fieldName != null, "The field name must not be null");
    // Sun Java 1.3 has a bugged implementation of getField hence we write the
    // code ourselves

    // getField() will return the Field object with the declaring class
    // set correctly to the class that declares the field. Thus requesting the
    // field on a subclass will return the field from the superclass.
    //
    // priority order for lookup:
    // searchclass private/protected/package/public
    // superclass protected/package/public
    // private/different package blocks access to further superclasses
    // implementedinterface public

    // check up the superclass hierarchy
    for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
      try {
        Field field = getDeclaredField(fieldName, acls);
        // getDeclaredField checks for non-public scopes as well and it returns accurate results
        if (!Modifier.isPublic(field.getModifiers())) {
          if (forceAccess) {
            field.setAccessible(true);
          } else {
            throw new IllegalAccessException("can not access" + fieldName + " because it is not public");
          }
        }
        return field;
      } catch (NoSuchFieldException ex) { // NOPMD
        // ignore
      }
    }
    // check the public interface case. This must be manually searched for
    // incase there is a public supersuperclass field hidden by a private/package
    // superclass field.
    Field match = null;
    for (Class<?> class1 : ClassUtils.getAllInterfaces(cls)) {
      try {
        Field test = class1.getField(fieldName);
        checkArgument(match == null, "Reference to field " + fieldName + " is ambiguous relative to " + cls
                                     + "; a matching field exists on two or more implemented interfaces.");
        match = test;
      } catch (NoSuchFieldException ex) { // NOPMD
        // ignore
      }
    }
    return match;
  }

  /**
   * Returns the {@link Field} corresponding to the given fieldName for the specified class.
   * <p>
   * Caches the field after getting it for efficiency.
   *
   * @param fieldName the name of the field to get
   * @param acls the class to introspect
   * @return the {@link Field} corresponding to the given fieldName for the specified class.
   * @throws NoSuchFieldException bubbled up from the call to {@link Class#getDeclaredField(String)}
   */
  private static Field getDeclaredField(String fieldName, Class<?> acls) throws NoSuchFieldException {
    fieldsPerClass.computeIfAbsent(acls, unused -> new ConcurrentHashMap<>());
    // can't use computeIfAbsent for fieldName as getDeclaredField throws a checked exception
    if (fieldsPerClass.get(acls).containsKey(fieldName)) return fieldsPerClass.get(acls).get(fieldName);
    Field field = acls.getDeclaredField(fieldName);
    fieldsPerClass.get(acls).put(fieldName, field);
    return acls.getDeclaredField(fieldName);
  }

  /**
   * Reads an accessible Field.
   *
   * @param field the field to use
   * @param target the object to call on, may be null for static fields
   * @return the field value
   * @throws IllegalArgumentException if the field is null
   * @throws IllegalAccessException if the field is not accessible
   */
  private static Object readField(Field field, Object target) throws IllegalAccessException {
    return readField(field, target, false);
  }

  /**
   * Reads a Field.
   *
   * @param field the field to use
   * @param target the object to call on, may be null for static fields
   * @param forceAccess whether to break scope restrictions using the <code>setAccessible</code> method.
   * @return the field value
   * @throws IllegalArgumentException if the field is null
   * @throws IllegalAccessException if the field is not made accessible
   */
  private static Object readField(Field field, Object target, boolean forceAccess) throws IllegalAccessException {
    checkArgument(field != null, "The field must not be null");
    if (forceAccess && !field.canAccess(target)) {
      field.setAccessible(true);
    } else {
      MemberUtils.setAccessibleWorkaround(field);
    }
    return field.get(target);
  }

  /**
   * Reads the named field. Superclasses will be considered.
   * <p>
   * Since 3.19.0 static and synthetic fields are ignored.
   *
   * @param target the object to reflect, must not be null
   * @param fieldName the field name to obtain
   * @param forceAccess whether to break scope restrictions using the <code>setAccessible</code> method.
   *          <code>False</code> will only match public fields.
   * @return the field value
   * @throws IllegalArgumentException if the class or field name is null or the field can not be found.
   * @throws IllegalAccessException if the named field is not made accessible
   */
  static Object readField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
    checkArgument(target != null, "target object must not be null");
    Class<?> cls = target.getClass();
    Field field = getField(cls, fieldName, forceAccess);
    checkArgument(field != null, "Cannot locate field %s on %s", fieldName, cls);
    checkArgument(!isStatic(field.getModifiers()), "Reading static field is not supported and field %s is static on %s",
                  fieldName, cls);
    checkArgument(!field.isSynthetic(), "Reading synthetic field is not supported and field %s is", fieldName);
    // already forced access above, don't repeat it here:
    return readField(field, target);
  }

}
