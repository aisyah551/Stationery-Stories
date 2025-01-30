package dev.aisyah.stationery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "sales-report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleStats {
    @Id
    private ObjectId id;
    private List<Stationery> productsReport;
    private Stationery highestSellingProduct;
    private Date date;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<Stationery> getProductsReport() {
        return productsReport;
    }

    public void setProductsReport(List<Stationery> productsReport) {
        this.productsReport = productsReport;
    }

    public Stationery getHighestSellingProduct() {
        return highestSellingProduct;
    }

    public void setHighestSellingProduct(Stationery highestSellingProduct) {
        this.highestSellingProduct = highestSellingProduct;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
