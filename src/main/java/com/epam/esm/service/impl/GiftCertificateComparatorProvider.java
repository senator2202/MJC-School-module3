package com.epam.esm.service.impl;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.Comparator;
import java.util.Optional;

class GiftCertificateComparatorProvider {
    private static final String DESC = "desc";
    private static final String ASC = "asc";

    static Optional<Comparator<GiftCertificate>> provide(String type, String direction) {
        Optional<Comparator<GiftCertificate>> optional;
        if (type != null) {
            try {
                SortType sortType = SortType.valueOf(type.toUpperCase());
                if (direction != null) {
                    if (direction.equals(DESC)) {
                        optional = Optional.of(sortType.getComparatorDesc());
                    } else if (direction.equals(ASC)) {
                        optional = Optional.of(sortType.getComparatorAsc());
                    } else {
                        optional = Optional.empty();
                    }
                } else {
                    optional = Optional.of(sortType.getComparatorAsc());
                }
            } catch (IllegalArgumentException e) {
                optional = Optional.empty();
            }
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    private enum SortType {
        NAME((c1, c2) -> c1.getName().compareTo(c2.getName())),
        CREATE_DATE((c1, c2) -> c1.getCreateDate().compareTo(c2.getCreateDate())),
        LAST_UPDATE_DATE((c1, c2) -> c1.getLastUpdateDate().compareTo(c2.getLastUpdateDate())),
        PRICE((c1, c2) -> c1.getPrice() - c2.getPrice()),
        DURATION((c1, c2) -> c1.getDuration() - c2.getDuration());

        private final Comparator<GiftCertificate> comparator;

        SortType(Comparator<GiftCertificate> comparator) {
            this.comparator = comparator;
        }

        public Comparator<GiftCertificate> getComparatorAsc() {
            return comparator;
        }

        public Comparator<GiftCertificate> getComparatorDesc() {
            return comparator.reversed();
        }
    }
}
