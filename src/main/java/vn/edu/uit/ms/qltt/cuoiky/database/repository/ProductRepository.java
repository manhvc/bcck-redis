package vn.edu.uit.ms.qltt.cuoiky.database.repository;

import org.springframework.data.repository.CrudRepository;
import vn.edu.uit.ms.qltt.cuoiky.database.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
