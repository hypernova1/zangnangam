package org.sam.melchor.repository.specs;

import org.sam.melchor.domain.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostSpecs {

    public enum SearchKey {
        TITLE("title"),
        WRITER("writer"),
        LIKESGREATERTHAN("likes");

        private final String value;

        SearchKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static Specification<Post> searchWith(Map<SearchKey, Object> searchKey) {
        return (Specification<Post>) ((root, query, criteriaBuilder) -> {
            List<Predicate> predicate = getPredicateWithKeyword(searchKey, root, criteriaBuilder);
            return criteriaBuilder.and(predicate.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicateWithKeyword(
            Map<SearchKey, Object> searchKeyword,
            Root<Post> root, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicate = new ArrayList<>();
        for (SearchKey key: searchKeyword.keySet()) {
            switch (key) {
                case TITLE:
                case WRITER:
                    predicate.add(criteriaBuilder.equal(
                            root.get(key.value), searchKeyword.get(key)
                    ));
                    break;
                case LIKESGREATERTHAN:
                    predicate.add(criteriaBuilder.greaterThan(
                            root.get(key.value), Integer.valueOf(searchKeyword.get(key).toString())
                    ));
                    break;
            }
        }
        return predicate;

    }

}
