package com.espacogeek.geek.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.repositories.MediaRepositoryCustom;
import com.espacogeek.geek.utils.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

@Repository
public class MediaRepositoryCustomImpl implements MediaRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @see MediaRepositoryCustom#findMediaByNameOrAlternativeTitleAndMediaCategory(String,
     *      String, Integer, Map)
     */
    @Override
    public List<MediaModel> findMediaByNameOrAlternativeTitleAndMediaCategory(
            String name,
            String alternativeTitle,
            Integer category,
            Map<String, List<String>> requestedFields) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MediaModel> query = cb.createQuery(MediaModel.class);
        Root<MediaModel> mediaRoot = query.from(MediaModel.class);
        Join<MediaModel, AlternativeTitleModel> altTitlesJoin = mediaRoot.join("alternativeTitles", JoinType.LEFT);

        List<Selection<?>> select = new ArrayList<>();
        requestedFields.forEach((field, subFields) -> {
            if (Utils.isJoinableField(mediaRoot, field)) {
                Join<Object, Object> join = mediaRoot.join(field, JoinType.LEFT);
                join.alias(field);
                subFields.forEach(subField -> {
                    select.add(join.get(subField).alias(subField));
                });
            } else {
                select.add(mediaRoot.get(field).alias(field));
            }
        });
        query.distinct(true);
        query.multiselect(select);

        List<Predicate> predicates = new ArrayList<>();
        if (category != null) {
            predicates.add(cb.equal(mediaRoot.get("mediaCategory").get("idMediaCategory"), category));
        }

        List<Predicate> namePredicates = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(mediaRoot.get("nameMedia"), "%" + name + "%"));
        }
        if (alternativeTitle != null) {
            predicates.add(cb.like(altTitlesJoin.get("nameAlternativeTitle"), "%" + alternativeTitle + "%"));
        }

        if (!namePredicates.isEmpty()) {
            Predicate orPredicate = cb.or(namePredicates.toArray(new Predicate[0]));
            predicates.add(orPredicate);
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        List<MediaModel> medias = entityManager.createQuery(query).getResultList();

        return medias;
    }
}
