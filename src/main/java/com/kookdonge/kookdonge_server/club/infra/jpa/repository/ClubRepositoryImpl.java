package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.QClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ClubRepositoryImpl implements ClubRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ClubEntity> findAllClubs(
            ClubCategory category,
            ClubType type,
            RecruitmentStatus recruitmentStatus,
            Integer targetGraduate,
            Integer weeklyActiveFrequency,
            String query,
            Pageable pageable
    ) {

        QClubEntity club = QClubEntity.clubEntity;

        BooleanExpression condition = buildCondition(
                club,
                category,
                type,
                recruitmentStatus,
                targetGraduate,
                weeklyActiveFrequency,
                query
        );

        List<OrderSpecifier<?>> orders = getOrderSpecifiers(pageable.getSort(), club);

        List<ClubEntity> content = queryFactory.selectFrom(club)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(club.count()).from(club).where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression buildCondition(
            QClubEntity club,
            ClubCategory category,
            ClubType type,
            RecruitmentStatus recruitmentStatus,
            Integer targetGraduate,
            Integer weeklyActiveFrequency,
            String query
    ) {

        return BooleanExpressionBuilder.create()
                .and(eqCategory(club, category))
                .and(eqClubType(club, type))
                .and(eqRecruitmentStatus(club, recruitmentStatus))
                .and(containsTargetGraduate(club, targetGraduate))
                .and(goeWeeklyActivityFrequency(club, weeklyActiveFrequency))
                .and(containsQuery(club, query))
                .build();
    }

    private static class BooleanExpressionBuilder {
        private BooleanExpression result;

        public static BooleanExpressionBuilder create() {
            return new BooleanExpressionBuilder();
        }

        public BooleanExpressionBuilder and(BooleanExpression expression) {
            if (expression == null) {
                return this;
            }
            result = result == null ? expression : result.and(expression);
            return this;
        }

        public BooleanExpression build() {
            return result;
        }
    }

    private BooleanExpression eqCategory(QClubEntity club, ClubCategory category) {

        return category != null ? club.category.eq(category) : null;
    }

    private BooleanExpression eqClubType(QClubEntity club, ClubType type) {

        return type != null ? club.clubType.eq(type) : null;
    }

    private BooleanExpression eqRecruitmentStatus(QClubEntity club, RecruitmentStatus recruitmentStatus) {

        return recruitmentStatus != null ? club.recruitmentStatus.eq(recruitmentStatus) : null;
    }

    private BooleanExpression containsTargetGraduate(QClubEntity club, Integer targetGraduate) {

        return targetGraduate != null ? club.targetGraduate.contains(String.valueOf(targetGraduate)) : null;
    }

    private BooleanExpression goeWeeklyActivityFrequency(QClubEntity club, Integer weeklyActiveFrequency) {

        return weeklyActiveFrequency != null
                ? club.weeklyActivityFrequency.goe(weeklyActiveFrequency.doubleValue())
                : null;
    }

    private BooleanExpression containsQuery(QClubEntity club, String query) {

        if (query == null || query.isBlank()) {
            return null;
        }
        return club.clubName.containsIgnoreCase(query).or(club.description.containsIgnoreCase(query));
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort, QClubEntity club) {

        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (sort.isEmpty()) {
            orders.add(club.createdAt.desc());
            return orders;
        }

        for (Sort.Order order : sort) {
            ClubSortType sortType = ClubSortType.from(order.getProperty());
            boolean isAsc = order.isAscending();

            switch (sortType) {
                case LATEST -> orders.add(club.createdAt.desc());
                case POPULARITY -> orders.add(isAsc ? club.totalLikeCount.asc() : club.totalLikeCount.desc());
                case DEADLINE -> {
                    orders.add(getRecruitmentStatusOrder(club).asc());
                    orders.add(club.recruitmentEndTime.asc());
                }
            }
        }

        return orders;
    }

    private NumberExpression<Integer> getRecruitmentStatusOrder(QClubEntity club) {

        return new CaseBuilder().when(club.recruitmentStatus.eq(RecruitmentStatus.RECRUITING))
                .then(1)
                .when(club.recruitmentStatus.eq(RecruitmentStatus.SCHEDULED))
                .then(2)
                .when(club.recruitmentStatus.eq(RecruitmentStatus.CLOSED))
                .then(3)
                .otherwise(4);
    }
}