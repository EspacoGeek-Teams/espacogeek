package com.espacogeek.geek.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.repositories.MediaRepositoryCustom;
import com.espacogeek.geek.utils.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class MediaRepositoryCustomImpl implements MediaRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @see MediaRepositoryCustom#findMediaByNameOrAlternativeTitleAndMediaCategory(String,
     *      String, Integer, Map)
     */
    @Override
    public Page<MediaModel> findMediaByNameOrAlternativeTitleAndMediaCategory(
            String name,
            String alternativeTitle,
            Integer category,
            Map<String, List<String>> requestedFields,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MediaModel> query = cb.createQuery(MediaModel.class);
        Root<MediaModel> mediaRoot = query.from(MediaModel.class);
        mediaRoot.fetch("alternativeTitles");

        requestedFields.forEach((field, subFields) -> {
            if (!"alternativeTitles".equals(field) && Utils.isJoinableField(mediaRoot, field))
                mediaRoot.fetch(field);
        });
        query.select(mediaRoot).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        if (category != null) {
            predicates.add(cb.equal(mediaRoot.get("mediaCategory").get("idMediaCategory"), category));
        }

        List<Predicate> namePredicates = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(mediaRoot.get("nameMedia"), "%" + name + "%"));
        }
        if (alternativeTitle != null) {
            predicates.add(cb.like(mediaRoot.get("alternativeTitles").get("nameAlternativeTitle"), "%" + alternativeTitle + "%"));
        }

        if (!namePredicates.isEmpty()) {
            Predicate orPredicate = cb.or(namePredicates.toArray(new Predicate[0]));
            predicates.add(orPredicate);
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        TypedQuery<MediaModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<MediaModel> medias = typedQuery.getResultList();

        return new PageImpl<>(medias, pageable, 0);
    }
}
