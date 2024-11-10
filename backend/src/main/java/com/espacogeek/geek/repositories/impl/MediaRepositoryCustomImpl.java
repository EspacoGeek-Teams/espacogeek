package com.espacogeek.geek.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.sql.results.internal.TupleImpl;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.repositories.MediaRepositoryCustom;
import com.espacogeek.geek.utils.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class MediaRepositoryCustomImpl implements MediaRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @see MediaRepositoryCustom#findMediaByNameOrAlternativeTitleAndMediaCategory(String, String, Integer, Map)
     */
    @Override
    public List<MediaModel> findMediaByNameOrAlternativeTitleAndMediaCategory(
            String name,
            String alternativeTitle,
            Integer category,
            Map<String, List<String>> requestedFields) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);
        Root<MediaModel> mediaRoot = query.from(MediaModel.class);
        Join<MediaModel, AlternativeTitleModel> altTitlesJoin = mediaRoot.join("alternativeTitles", JoinType.LEFT);

        requestedFields.forEach((field, subFields) -> {
            if (Utils.isJoinableField(mediaRoot, field)) {
                Join<Object, Object> join = mediaRoot.join(field, JoinType.LEFT);
                join.alias(field);
                subFields.forEach(subField -> {
                    query.multiselect(join.get(subField).alias(subField));
                });
            } else {
                query.multiselect(mediaRoot.get(field).alias(field));
            }
        });

        List<Predicate> predicates = new ArrayList<>();
        if (category != null) {
            predicates.add(cb.equal(mediaRoot.get("mediaCategory").get("id"), category));
        }

        List<Predicate> namePredicates = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(mediaRoot.get("name"), "%" + name + "%"));
        }
        if (alternativeTitle != null) {
            predicates.add(cb.like(altTitlesJoin.get("name"), "%" + alternativeTitle + "%"));
        }

        if (!namePredicates.isEmpty()) {
            Predicate orPredicate = cb.or(namePredicates.toArray(new Predicate[0]));
            predicates.add(orPredicate);
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        List<MediaModel> medias = new ArrayList<>();
        entityManager.createQuery(query).getResultList().forEach((tuple) -> {
            medias.add(Utils.fromTuple(tuple, MediaModel.class));
        });

        return medias;
    }
}
