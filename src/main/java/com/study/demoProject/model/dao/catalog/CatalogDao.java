package com.study.demoProject.model.dao.catalog;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.demoProject.controller.Sorter;
import com.study.demoProject.model.dto.catalog.CatalogSummary;
import com.study.demoProject.model.dto.catalog.QCatalogSummary;
import com.study.demoProject.service.item.ItemSearchForm;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.study.demoProject.domain.item.QItemEntity.itemEntity;
import static com.study.demoProject.domain.review.QReviewProductEntity.reviewProductEntity;

@Repository
public class CatalogDao {
    private JPAQueryFactory query;

    public CatalogDao(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    public List<CatalogSummary> searchItem(ItemSearchForm searchForm)  {
        return query
                .select(new QCatalogSummary(itemEntity.itemId, itemEntity.imagePath, itemEntity.name, itemEntity.price,
                        reviewProductEntity.ratingAverage, reviewProductEntity.totalCount))
                .from(itemEntity)
                .join(reviewProductEntity)
                .on(itemEntity.itemId.eq(reviewProductEntity.productId))
                .where(nameLike(searchForm.getName()), categoryEq(searchForm.getCategoryId()))
                .orderBy(sorter(searchForm.getSorter()))
                .fetch();
    }

    private Predicate nameLike(String name) {
        if (name != null && name.length() > 0)
            return itemEntity.name.like("%" + name + "%");
        return null;
    }

    private Predicate categoryEq(Long categoryId) {
        if (categoryId != null)
            return itemEntity.categoryId.eq(categoryId);
        return null;
    }

    private OrderSpecifier sorter(Sorter sorter) {
        if (sorter == null)
            return itemEntity.createdDate.desc();

        if (sorter == Sorter.PRICE)
            return itemEntity.price.desc();

        if (sorter == Sorter.LATEST)
            return itemEntity.createdDate.desc();

        return itemEntity.createdDate.desc();
    }
}
