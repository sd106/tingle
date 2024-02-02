package com.example.tingle.fanMeeting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFanMeetingType is a Querydsl query type for FanMeetingType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFanMeetingType extends EntityPathBase<FanMeetingType> {

    private static final long serialVersionUID = -61080579L;

    public static final QFanMeetingType fanMeetingType = new QFanMeetingType("fanMeetingType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QFanMeetingType(String variable) {
        super(FanMeetingType.class, forVariable(variable));
    }

    public QFanMeetingType(Path<? extends FanMeetingType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFanMeetingType(PathMetadata metadata) {
        super(FanMeetingType.class, metadata);
    }

}

