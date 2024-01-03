package com.example.androidpart.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.androidpart.repository.product.TrashPlusContractProduct;

@Entity(tableName = TrashPlusContractProduct.ProductEntry.TABLE_NAME)
public class Product{

    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_COVER_CODE)
    private String classOfCover;

    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_USER_ID)
    private long userId;

    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_JUST_ADDED)
    private boolean justAdded = false;

    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_NAME)
    private String nameOfProduct;

    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_INFO)
    private String information;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_ID)
    private long id;

    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_PRODUCT_CODE)
    private long productCode;

    @ColumnInfo(name = TrashPlusContractProduct.ProductEntry.COLUMN_PHOTO_LINK)
    private String photoLink;

    public boolean isJustAdded() {
        return justAdded;
    }

    public void setJustAdded(boolean justAdded) {
        this.justAdded = justAdded;
    }

    @Ignore
    public Product(String nameOfProduct, long productCode, String information, String photoLink) {
        this.nameOfProduct = nameOfProduct;
        this.productCode = productCode;
        this.information = information;
        this.photoLink = photoLink;
    }

    public Product(String nameOfProduct, long productCode, String information, String photoLink,
                   String classOfCover) {
        this.nameOfProduct = nameOfProduct;
        this.productCode = productCode;
        this.information = information;
        this.photoLink = photoLink;
        this.classOfCover = classOfCover;
    }

    public String getClassOfCover() {
        return classOfCover;
    }

    public void setClassOfCover(String classOfCover) {
        this.classOfCover = classOfCover;
    }

    @Ignore
    public Product(long id, String nameOfProduct, long productCode, String information, String photoLink, long userId) {
        this.id = id;
        this.nameOfProduct = nameOfProduct;
        this.productCode = productCode;
        this.information = information;
        this.photoLink = photoLink;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public long getProductCode() {
        return productCode;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameOfProduct='" + nameOfProduct + '\'' +
                ", productCode=" + productCode +
                ", information='" + information + '\'' +
                ", classOfCover=" + classOfCover + '\'' +
                ", justAdded=" + justAdded + '\'' +
                ", photoLink='" + photoLink + '\'' +
                '}';
    }
}
