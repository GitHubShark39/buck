/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.facebook.buck.distributed.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-07-25")
public class BuildJobStateBuildTarget implements org.apache.thrift.TBase<BuildJobStateBuildTarget, BuildJobStateBuildTarget._Fields>, java.io.Serializable, Cloneable, Comparable<BuildJobStateBuildTarget> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BuildJobStateBuildTarget");

  private static final org.apache.thrift.protocol.TField CELL_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("cellName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField BASE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("baseName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SHORT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("shortName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FLAVORS_FIELD_DESC = new org.apache.thrift.protocol.TField("flavors", org.apache.thrift.protocol.TType.SET, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BuildJobStateBuildTargetStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BuildJobStateBuildTargetTupleSchemeFactory());
  }

  public String cellName; // optional
  public String baseName; // required
  public String shortName; // required
  public Set<String> flavors; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CELL_NAME((short)1, "cellName"),
    BASE_NAME((short)2, "baseName"),
    SHORT_NAME((short)3, "shortName"),
    FLAVORS((short)4, "flavors");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CELL_NAME
          return CELL_NAME;
        case 2: // BASE_NAME
          return BASE_NAME;
        case 3: // SHORT_NAME
          return SHORT_NAME;
        case 4: // FLAVORS
          return FLAVORS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.CELL_NAME};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CELL_NAME, new org.apache.thrift.meta_data.FieldMetaData("cellName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BASE_NAME, new org.apache.thrift.meta_data.FieldMetaData("baseName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SHORT_NAME, new org.apache.thrift.meta_data.FieldMetaData("shortName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FLAVORS, new org.apache.thrift.meta_data.FieldMetaData("flavors", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BuildJobStateBuildTarget.class, metaDataMap);
  }

  public BuildJobStateBuildTarget() {
  }

  public BuildJobStateBuildTarget(
    String baseName,
    String shortName,
    Set<String> flavors)
  {
    this();
    this.baseName = baseName;
    this.shortName = shortName;
    this.flavors = flavors;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BuildJobStateBuildTarget(BuildJobStateBuildTarget other) {
    if (other.isSetCellName()) {
      this.cellName = other.cellName;
    }
    if (other.isSetBaseName()) {
      this.baseName = other.baseName;
    }
    if (other.isSetShortName()) {
      this.shortName = other.shortName;
    }
    if (other.isSetFlavors()) {
      Set<String> __this__flavors = new HashSet<String>(other.flavors);
      this.flavors = __this__flavors;
    }
  }

  public BuildJobStateBuildTarget deepCopy() {
    return new BuildJobStateBuildTarget(this);
  }

  @Override
  public void clear() {
    this.cellName = null;
    this.baseName = null;
    this.shortName = null;
    this.flavors = null;
  }

  public String getCellName() {
    return this.cellName;
  }

  public BuildJobStateBuildTarget setCellName(String cellName) {
    this.cellName = cellName;
    return this;
  }

  public void unsetCellName() {
    this.cellName = null;
  }

  /** Returns true if field cellName is set (has been assigned a value) and false otherwise */
  public boolean isSetCellName() {
    return this.cellName != null;
  }

  public void setCellNameIsSet(boolean value) {
    if (!value) {
      this.cellName = null;
    }
  }

  public String getBaseName() {
    return this.baseName;
  }

  public BuildJobStateBuildTarget setBaseName(String baseName) {
    this.baseName = baseName;
    return this;
  }

  public void unsetBaseName() {
    this.baseName = null;
  }

  /** Returns true if field baseName is set (has been assigned a value) and false otherwise */
  public boolean isSetBaseName() {
    return this.baseName != null;
  }

  public void setBaseNameIsSet(boolean value) {
    if (!value) {
      this.baseName = null;
    }
  }

  public String getShortName() {
    return this.shortName;
  }

  public BuildJobStateBuildTarget setShortName(String shortName) {
    this.shortName = shortName;
    return this;
  }

  public void unsetShortName() {
    this.shortName = null;
  }

  /** Returns true if field shortName is set (has been assigned a value) and false otherwise */
  public boolean isSetShortName() {
    return this.shortName != null;
  }

  public void setShortNameIsSet(boolean value) {
    if (!value) {
      this.shortName = null;
    }
  }

  public int getFlavorsSize() {
    return (this.flavors == null) ? 0 : this.flavors.size();
  }

  public java.util.Iterator<String> getFlavorsIterator() {
    return (this.flavors == null) ? null : this.flavors.iterator();
  }

  public void addToFlavors(String elem) {
    if (this.flavors == null) {
      this.flavors = new HashSet<String>();
    }
    this.flavors.add(elem);
  }

  public Set<String> getFlavors() {
    return this.flavors;
  }

  public BuildJobStateBuildTarget setFlavors(Set<String> flavors) {
    this.flavors = flavors;
    return this;
  }

  public void unsetFlavors() {
    this.flavors = null;
  }

  /** Returns true if field flavors is set (has been assigned a value) and false otherwise */
  public boolean isSetFlavors() {
    return this.flavors != null;
  }

  public void setFlavorsIsSet(boolean value) {
    if (!value) {
      this.flavors = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CELL_NAME:
      if (value == null) {
        unsetCellName();
      } else {
        setCellName((String)value);
      }
      break;

    case BASE_NAME:
      if (value == null) {
        unsetBaseName();
      } else {
        setBaseName((String)value);
      }
      break;

    case SHORT_NAME:
      if (value == null) {
        unsetShortName();
      } else {
        setShortName((String)value);
      }
      break;

    case FLAVORS:
      if (value == null) {
        unsetFlavors();
      } else {
        setFlavors((Set<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CELL_NAME:
      return getCellName();

    case BASE_NAME:
      return getBaseName();

    case SHORT_NAME:
      return getShortName();

    case FLAVORS:
      return getFlavors();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CELL_NAME:
      return isSetCellName();
    case BASE_NAME:
      return isSetBaseName();
    case SHORT_NAME:
      return isSetShortName();
    case FLAVORS:
      return isSetFlavors();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BuildJobStateBuildTarget)
      return this.equals((BuildJobStateBuildTarget)that);
    return false;
  }

  public boolean equals(BuildJobStateBuildTarget that) {
    if (that == null)
      return false;

    boolean this_present_cellName = true && this.isSetCellName();
    boolean that_present_cellName = true && that.isSetCellName();
    if (this_present_cellName || that_present_cellName) {
      if (!(this_present_cellName && that_present_cellName))
        return false;
      if (!this.cellName.equals(that.cellName))
        return false;
    }

    boolean this_present_baseName = true && this.isSetBaseName();
    boolean that_present_baseName = true && that.isSetBaseName();
    if (this_present_baseName || that_present_baseName) {
      if (!(this_present_baseName && that_present_baseName))
        return false;
      if (!this.baseName.equals(that.baseName))
        return false;
    }

    boolean this_present_shortName = true && this.isSetShortName();
    boolean that_present_shortName = true && that.isSetShortName();
    if (this_present_shortName || that_present_shortName) {
      if (!(this_present_shortName && that_present_shortName))
        return false;
      if (!this.shortName.equals(that.shortName))
        return false;
    }

    boolean this_present_flavors = true && this.isSetFlavors();
    boolean that_present_flavors = true && that.isSetFlavors();
    if (this_present_flavors || that_present_flavors) {
      if (!(this_present_flavors && that_present_flavors))
        return false;
      if (!this.flavors.equals(that.flavors))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_cellName = true && (isSetCellName());
    list.add(present_cellName);
    if (present_cellName)
      list.add(cellName);

    boolean present_baseName = true && (isSetBaseName());
    list.add(present_baseName);
    if (present_baseName)
      list.add(baseName);

    boolean present_shortName = true && (isSetShortName());
    list.add(present_shortName);
    if (present_shortName)
      list.add(shortName);

    boolean present_flavors = true && (isSetFlavors());
    list.add(present_flavors);
    if (present_flavors)
      list.add(flavors);

    return list.hashCode();
  }

  @Override
  public int compareTo(BuildJobStateBuildTarget other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCellName()).compareTo(other.isSetCellName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCellName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cellName, other.cellName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBaseName()).compareTo(other.isSetBaseName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBaseName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.baseName, other.baseName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShortName()).compareTo(other.isSetShortName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShortName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shortName, other.shortName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFlavors()).compareTo(other.isSetFlavors());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFlavors()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.flavors, other.flavors);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("BuildJobStateBuildTarget(");
    boolean first = true;

    if (isSetCellName()) {
      sb.append("cellName:");
      if (this.cellName == null) {
        sb.append("null");
      } else {
        sb.append(this.cellName);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("baseName:");
    if (this.baseName == null) {
      sb.append("null");
    } else {
      sb.append(this.baseName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("shortName:");
    if (this.shortName == null) {
      sb.append("null");
    } else {
      sb.append(this.shortName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("flavors:");
    if (this.flavors == null) {
      sb.append("null");
    } else {
      sb.append(this.flavors);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class BuildJobStateBuildTargetStandardSchemeFactory implements SchemeFactory {
    public BuildJobStateBuildTargetStandardScheme getScheme() {
      return new BuildJobStateBuildTargetStandardScheme();
    }
  }

  private static class BuildJobStateBuildTargetStandardScheme extends StandardScheme<BuildJobStateBuildTarget> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BuildJobStateBuildTarget struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CELL_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.cellName = iprot.readString();
              struct.setCellNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BASE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.baseName = iprot.readString();
              struct.setBaseNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SHORT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.shortName = iprot.readString();
              struct.setShortNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FLAVORS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set44 = iprot.readSetBegin();
                struct.flavors = new HashSet<String>(2*_set44.size);
                String _elem45;
                for (int _i46 = 0; _i46 < _set44.size; ++_i46)
                {
                  _elem45 = iprot.readString();
                  struct.flavors.add(_elem45);
                }
                iprot.readSetEnd();
              }
              struct.setFlavorsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, BuildJobStateBuildTarget struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.cellName != null) {
        if (struct.isSetCellName()) {
          oprot.writeFieldBegin(CELL_NAME_FIELD_DESC);
          oprot.writeString(struct.cellName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.baseName != null) {
        oprot.writeFieldBegin(BASE_NAME_FIELD_DESC);
        oprot.writeString(struct.baseName);
        oprot.writeFieldEnd();
      }
      if (struct.shortName != null) {
        oprot.writeFieldBegin(SHORT_NAME_FIELD_DESC);
        oprot.writeString(struct.shortName);
        oprot.writeFieldEnd();
      }
      if (struct.flavors != null) {
        oprot.writeFieldBegin(FLAVORS_FIELD_DESC);
        {
          oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, struct.flavors.size()));
          for (String _iter47 : struct.flavors)
          {
            oprot.writeString(_iter47);
          }
          oprot.writeSetEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BuildJobStateBuildTargetTupleSchemeFactory implements SchemeFactory {
    public BuildJobStateBuildTargetTupleScheme getScheme() {
      return new BuildJobStateBuildTargetTupleScheme();
    }
  }

  private static class BuildJobStateBuildTargetTupleScheme extends TupleScheme<BuildJobStateBuildTarget> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BuildJobStateBuildTarget struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCellName()) {
        optionals.set(0);
      }
      if (struct.isSetBaseName()) {
        optionals.set(1);
      }
      if (struct.isSetShortName()) {
        optionals.set(2);
      }
      if (struct.isSetFlavors()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetCellName()) {
        oprot.writeString(struct.cellName);
      }
      if (struct.isSetBaseName()) {
        oprot.writeString(struct.baseName);
      }
      if (struct.isSetShortName()) {
        oprot.writeString(struct.shortName);
      }
      if (struct.isSetFlavors()) {
        {
          oprot.writeI32(struct.flavors.size());
          for (String _iter48 : struct.flavors)
          {
            oprot.writeString(_iter48);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BuildJobStateBuildTarget struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.cellName = iprot.readString();
        struct.setCellNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.baseName = iprot.readString();
        struct.setBaseNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.shortName = iprot.readString();
        struct.setShortNameIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TSet _set49 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.flavors = new HashSet<String>(2*_set49.size);
          String _elem50;
          for (int _i51 = 0; _i51 < _set49.size; ++_i51)
          {
            _elem50 = iprot.readString();
            struct.flavors.add(_elem50);
          }
        }
        struct.setFlavorsIsSet(true);
      }
    }
  }

}

