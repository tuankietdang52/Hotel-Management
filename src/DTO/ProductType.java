package DTO;

public class ProductType {
    private String typeCode;
    private String typeName;

    public ProductType(){

    }

    public ProductType(String typeCode, String typeName){
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
