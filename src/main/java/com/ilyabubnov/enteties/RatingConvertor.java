package com.ilyabubnov.enteties;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true)
public class RatingConvertor implements AttributeConverter <Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute.getValue();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
       Rating [] allRatingValues = Rating.values();
        for (Rating ratingValue : allRatingValues) {
            if (ratingValue.getValue().equals(dbData)) {
                return ratingValue;
            }
        }
        return null;
    }
}
