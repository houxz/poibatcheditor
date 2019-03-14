package cn.emg.poibatcheditor.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class POIModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public POIModelExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOidIsNull() {
            addCriterion("oid is null");
            return (Criteria) this;
        }

        public Criteria andOidIsNotNull() {
            addCriterion("oid is not null");
            return (Criteria) this;
        }

        public Criteria andOidEqualTo(Long value) {
            addCriterion("oid =", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotEqualTo(Long value) {
            addCriterion("oid <>", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThan(Long value) {
            addCriterion("oid >", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThanOrEqualTo(Long value) {
            addCriterion("oid >=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThan(Long value) {
            addCriterion("oid <", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThanOrEqualTo(Long value) {
            addCriterion("oid <=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidIn(List<Long> values) {
            addCriterion("oid in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotIn(List<Long> values) {
            addCriterion("oid not in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidBetween(Long value1, Long value2) {
            addCriterion("oid between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotBetween(Long value1, Long value2) {
            addCriterion("oid not between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andFeatcodeIsNull() {
            addCriterion("featcode is null");
            return (Criteria) this;
        }

        public Criteria andFeatcodeIsNotNull() {
            addCriterion("featcode is not null");
            return (Criteria) this;
        }

        public Criteria andFeatcodeEqualTo(Long value) {
            addCriterion("featcode =", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeNotEqualTo(Long value) {
            addCriterion("featcode <>", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeGreaterThan(Long value) {
            addCriterion("featcode >", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeGreaterThanOrEqualTo(Long value) {
            addCriterion("featcode >=", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeLessThan(Long value) {
            addCriterion("featcode <", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeLessThanOrEqualTo(Long value) {
            addCriterion("featcode <=", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeIn(List<Long> values) {
            addCriterion("featcode in", values, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeNotIn(List<Long> values) {
            addCriterion("featcode not in", values, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeBetween(Long value1, Long value2) {
            addCriterion("featcode between", value1, value2, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeNotBetween(Long value1, Long value2) {
            addCriterion("featcode not between", value1, value2, "featcode");
            return (Criteria) this;
        }

        public Criteria andNamecIsNull() {
            addCriterion("namec is null");
            return (Criteria) this;
        }

        public Criteria andNamecIsNotNull() {
            addCriterion("namec is not null");
            return (Criteria) this;
        }

        public Criteria andNamecEqualTo(String value) {
            addCriterion("namec =", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecNotEqualTo(String value) {
            addCriterion("namec <>", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecGreaterThan(String value) {
            addCriterion("namec >", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecGreaterThanOrEqualTo(String value) {
            addCriterion("namec >=", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecLessThan(String value) {
            addCriterion("namec <", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecLessThanOrEqualTo(String value) {
            addCriterion("namec <=", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecLike(String value) {
            addCriterion("namec like", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecNotLike(String value) {
            addCriterion("namec not like", value, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecIn(List<String> values) {
            addCriterion("namec in", values, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecNotIn(List<String> values) {
            addCriterion("namec not in", values, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecBetween(String value1, String value2) {
            addCriterion("namec between", value1, value2, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecNotBetween(String value1, String value2) {
            addCriterion("namec not between", value1, value2, "namec");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorIsNull() {
            addCriterion("namec_tsvector is null");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorIsNotNull() {
            addCriterion("namec_tsvector is not null");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorEqualTo(Object value) {
            addCriterion("namec_tsvector =", value, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorNotEqualTo(Object value) {
            addCriterion("namec_tsvector <>", value, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorGreaterThan(Object value) {
            addCriterion("namec_tsvector >", value, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorGreaterThanOrEqualTo(Object value) {
            addCriterion("namec_tsvector >=", value, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorLessThan(Object value) {
            addCriterion("namec_tsvector <", value, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorLessThanOrEqualTo(Object value) {
            addCriterion("namec_tsvector <=", value, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorIn(List<Object> values) {
            addCriterion("namec_tsvector in", values, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorNotIn(List<Object> values) {
            addCriterion("namec_tsvector not in", values, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorBetween(Object value1, Object value2) {
            addCriterion("namec_tsvector between", value1, value2, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andNamecTsvectorNotBetween(Object value1, Object value2) {
            addCriterion("namec_tsvector not between", value1, value2, "namecTsvector");
            return (Criteria) this;
        }

        public Criteria andDatasourceIsNull() {
            addCriterion("datasource is null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIsNotNull() {
            addCriterion("datasource is not null");
            return (Criteria) this;
        }

        public Criteria andDatasourceEqualTo(Object value) {
            addCriterion("datasource =", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotEqualTo(Object value) {
            addCriterion("datasource <>", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceGreaterThan(Object value) {
            addCriterion("datasource >", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceGreaterThanOrEqualTo(Object value) {
            addCriterion("datasource >=", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLessThan(Object value) {
            addCriterion("datasource <", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceLessThanOrEqualTo(Object value) {
            addCriterion("datasource <=", value, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceIn(List<Object> values) {
            addCriterion("datasource in", values, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotIn(List<Object> values) {
            addCriterion("datasource not in", values, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceBetween(Object value1, Object value2) {
            addCriterion("datasource between", value1, value2, "datasource");
            return (Criteria) this;
        }

        public Criteria andDatasourceNotBetween(Object value1, Object value2) {
            addCriterion("datasource not between", value1, value2, "datasource");
            return (Criteria) this;
        }

        public Criteria andGradeIsNull() {
            addCriterion("grade is null");
            return (Criteria) this;
        }

        public Criteria andGradeIsNotNull() {
            addCriterion("grade is not null");
            return (Criteria) this;
        }

        public Criteria andGradeEqualTo(String value) {
            addCriterion("grade =", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotEqualTo(String value) {
            addCriterion("grade <>", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThan(String value) {
            addCriterion("grade >", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThanOrEqualTo(String value) {
            addCriterion("grade >=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThan(String value) {
            addCriterion("grade <", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThanOrEqualTo(String value) {
            addCriterion("grade <=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLike(String value) {
            addCriterion("grade like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotLike(String value) {
            addCriterion("grade not like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeIn(List<String> values) {
            addCriterion("grade in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotIn(List<String> values) {
            addCriterion("grade not in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeBetween(String value1, String value2) {
            addCriterion("grade between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotBetween(String value1, String value2) {
            addCriterion("grade not between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andVerIsNull() {
            addCriterion("ver is null");
            return (Criteria) this;
        }

        public Criteria andVerIsNotNull() {
            addCriterion("ver is not null");
            return (Criteria) this;
        }

        public Criteria andVerEqualTo(Date value) {
            addCriterion("ver =", value, "ver");
            return (Criteria) this;
        }

        public Criteria andVerNotEqualTo(Date value) {
            addCriterion("ver <>", value, "ver");
            return (Criteria) this;
        }

        public Criteria andVerGreaterThan(Date value) {
            addCriterion("ver >", value, "ver");
            return (Criteria) this;
        }

        public Criteria andVerGreaterThanOrEqualTo(Date value) {
            addCriterion("ver >=", value, "ver");
            return (Criteria) this;
        }

        public Criteria andVerLessThan(Date value) {
            addCriterion("ver <", value, "ver");
            return (Criteria) this;
        }

        public Criteria andVerLessThanOrEqualTo(Date value) {
            addCriterion("ver <=", value, "ver");
            return (Criteria) this;
        }

        public Criteria andVerIn(List<Date> values) {
            addCriterion("ver in", values, "ver");
            return (Criteria) this;
        }

        public Criteria andVerNotIn(List<Date> values) {
            addCriterion("ver not in", values, "ver");
            return (Criteria) this;
        }

        public Criteria andVerBetween(Date value1, Date value2) {
            addCriterion("ver between", value1, value2, "ver");
            return (Criteria) this;
        }

        public Criteria andVerNotBetween(Date value1, Date value2) {
            addCriterion("ver not between", value1, value2, "ver");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andOptypeIsNull() {
            addCriterion("optype is null");
            return (Criteria) this;
        }

        public Criteria andOptypeIsNotNull() {
            addCriterion("optype is not null");
            return (Criteria) this;
        }

        public Criteria andOptypeEqualTo(String value) {
            addCriterion("optype =", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeNotEqualTo(String value) {
            addCriterion("optype <>", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeGreaterThan(String value) {
            addCriterion("optype >", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeGreaterThanOrEqualTo(String value) {
            addCriterion("optype >=", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeLessThan(String value) {
            addCriterion("optype <", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeLessThanOrEqualTo(String value) {
            addCriterion("optype <=", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeLike(String value) {
            addCriterion("optype like", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeNotLike(String value) {
            addCriterion("optype not like", value, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeIn(List<String> values) {
            addCriterion("optype in", values, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeNotIn(List<String> values) {
            addCriterion("optype not in", values, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeBetween(String value1, String value2) {
            addCriterion("optype between", value1, value2, "optype");
            return (Criteria) this;
        }

        public Criteria andOptypeNotBetween(String value1, String value2) {
            addCriterion("optype not between", value1, value2, "optype");
            return (Criteria) this;
        }

        public Criteria andAutocheckIsNull() {
            addCriterion("autocheck is null");
            return (Criteria) this;
        }

        public Criteria andAutocheckIsNotNull() {
            addCriterion("autocheck is not null");
            return (Criteria) this;
        }

        public Criteria andAutocheckEqualTo(String value) {
            addCriterion("autocheck =", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckNotEqualTo(String value) {
            addCriterion("autocheck <>", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckGreaterThan(String value) {
            addCriterion("autocheck >", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckGreaterThanOrEqualTo(String value) {
            addCriterion("autocheck >=", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckLessThan(String value) {
            addCriterion("autocheck <", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckLessThanOrEqualTo(String value) {
            addCriterion("autocheck <=", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckLike(String value) {
            addCriterion("autocheck like", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckNotLike(String value) {
            addCriterion("autocheck not like", value, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckIn(List<String> values) {
            addCriterion("autocheck in", values, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckNotIn(List<String> values) {
            addCriterion("autocheck not in", values, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckBetween(String value1, String value2) {
            addCriterion("autocheck between", value1, value2, "autocheck");
            return (Criteria) this;
        }

        public Criteria andAutocheckNotBetween(String value1, String value2) {
            addCriterion("autocheck not between", value1, value2, "autocheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckIsNull() {
            addCriterion("manualcheck is null");
            return (Criteria) this;
        }

        public Criteria andManualcheckIsNotNull() {
            addCriterion("manualcheck is not null");
            return (Criteria) this;
        }

        public Criteria andManualcheckEqualTo(String value) {
            addCriterion("manualcheck =", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckNotEqualTo(String value) {
            addCriterion("manualcheck <>", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckGreaterThan(String value) {
            addCriterion("manualcheck >", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckGreaterThanOrEqualTo(String value) {
            addCriterion("manualcheck >=", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckLessThan(String value) {
            addCriterion("manualcheck <", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckLessThanOrEqualTo(String value) {
            addCriterion("manualcheck <=", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckLike(String value) {
            addCriterion("manualcheck like", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckNotLike(String value) {
            addCriterion("manualcheck not like", value, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckIn(List<String> values) {
            addCriterion("manualcheck in", values, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckNotIn(List<String> values) {
            addCriterion("manualcheck not in", values, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckBetween(String value1, String value2) {
            addCriterion("manualcheck between", value1, value2, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andManualcheckNotBetween(String value1, String value2) {
            addCriterion("manualcheck not between", value1, value2, "manualcheck");
            return (Criteria) this;
        }

        public Criteria andProcessIsNull() {
            addCriterion("process is null");
            return (Criteria) this;
        }

        public Criteria andProcessIsNotNull() {
            addCriterion("process is not null");
            return (Criteria) this;
        }

        public Criteria andProcessEqualTo(Object value) {
            addCriterion("process =", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotEqualTo(Object value) {
            addCriterion("process <>", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessGreaterThan(Object value) {
            addCriterion("process >", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessGreaterThanOrEqualTo(Object value) {
            addCriterion("process >=", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessLessThan(Object value) {
            addCriterion("process <", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessLessThanOrEqualTo(Object value) {
            addCriterion("process <=", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessIn(List<Object> values) {
            addCriterion("process in", values, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotIn(List<Object> values) {
            addCriterion("process not in", values, "process");
            return (Criteria) this;
        }

        public Criteria andProcessBetween(Object value1, Object value2) {
            addCriterion("process between", value1, value2, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotBetween(Object value1, Object value2) {
            addCriterion("process not between", value1, value2, "process");
            return (Criteria) this;
        }

        public Criteria andIsdelIsNull() {
            addCriterion("isdel is null");
            return (Criteria) this;
        }

        public Criteria andIsdelIsNotNull() {
            addCriterion("isdel is not null");
            return (Criteria) this;
        }

        public Criteria andIsdelEqualTo(Boolean value) {
            addCriterion("isdel =", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelNotEqualTo(Boolean value) {
            addCriterion("isdel <>", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelGreaterThan(Boolean value) {
            addCriterion("isdel >", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isdel >=", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelLessThan(Boolean value) {
            addCriterion("isdel <", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelLessThanOrEqualTo(Boolean value) {
            addCriterion("isdel <=", value, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelIn(List<Boolean> values) {
            addCriterion("isdel in", values, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelNotIn(List<Boolean> values) {
            addCriterion("isdel not in", values, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelBetween(Boolean value1, Boolean value2) {
            addCriterion("isdel between", value1, value2, "isdel");
            return (Criteria) this;
        }

        public Criteria andIsdelNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isdel not between", value1, value2, "isdel");
            return (Criteria) this;
        }

        public Criteria andProjectidIsNull() {
            addCriterion("projectid is null");
            return (Criteria) this;
        }

        public Criteria andProjectidIsNotNull() {
            addCriterion("projectid is not null");
            return (Criteria) this;
        }

        public Criteria andProjectidEqualTo(Long value) {
            addCriterion("projectid =", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidNotEqualTo(Long value) {
            addCriterion("projectid <>", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidGreaterThan(Long value) {
            addCriterion("projectid >", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidGreaterThanOrEqualTo(Long value) {
            addCriterion("projectid >=", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidLessThan(Long value) {
            addCriterion("projectid <", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidLessThanOrEqualTo(Long value) {
            addCriterion("projectid <=", value, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidIn(List<Long> values) {
            addCriterion("projectid in", values, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidNotIn(List<Long> values) {
            addCriterion("projectid not in", values, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidBetween(Long value1, Long value2) {
            addCriterion("projectid between", value1, value2, "projectid");
            return (Criteria) this;
        }

        public Criteria andProjectidNotBetween(Long value1, Long value2) {
            addCriterion("projectid not between", value1, value2, "projectid");
            return (Criteria) this;
        }

        public Criteria andEditverIsNull() {
            addCriterion("editver is null");
            return (Criteria) this;
        }

        public Criteria andEditverIsNotNull() {
            addCriterion("editver is not null");
            return (Criteria) this;
        }

        public Criteria andEditverEqualTo(Long value) {
            addCriterion("editver =", value, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverNotEqualTo(Long value) {
            addCriterion("editver <>", value, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverGreaterThan(Long value) {
            addCriterion("editver >", value, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverGreaterThanOrEqualTo(Long value) {
            addCriterion("editver >=", value, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverLessThan(Long value) {
            addCriterion("editver <", value, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverLessThanOrEqualTo(Long value) {
            addCriterion("editver <=", value, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverIn(List<Long> values) {
            addCriterion("editver in", values, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverNotIn(List<Long> values) {
            addCriterion("editver not in", values, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverBetween(Long value1, Long value2) {
            addCriterion("editver between", value1, value2, "editver");
            return (Criteria) this;
        }

        public Criteria andEditverNotBetween(Long value1, Long value2) {
            addCriterion("editver not between", value1, value2, "editver");
            return (Criteria) this;
        }

        public Criteria andGeoIsNull() {
            addCriterion("geo is null");
            return (Criteria) this;
        }

        public Criteria andGeoIsNotNull() {
            addCriterion("geo is not null");
            return (Criteria) this;
        }

        public Criteria andGeoEqualTo(Object value) {
            addCriterion("geo =", value, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoNotEqualTo(Object value) {
            addCriterion("geo <>", value, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoGreaterThan(Object value) {
            addCriterion("geo >", value, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoGreaterThanOrEqualTo(Object value) {
            addCriterion("geo >=", value, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoLessThan(Object value) {
            addCriterion("geo <", value, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoLessThanOrEqualTo(Object value) {
            addCriterion("geo <=", value, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoIn(List<Object> values) {
            addCriterion("geo in", values, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoNotIn(List<Object> values) {
            addCriterion("geo not in", values, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoBetween(Object value1, Object value2) {
            addCriterion("geo between", value1, value2, "geo");
            return (Criteria) this;
        }

        public Criteria andGeoNotBetween(Object value1, Object value2) {
            addCriterion("geo not between", value1, value2, "geo");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(Integer value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(Integer value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(Integer value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(Integer value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(Integer value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(Integer value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<Integer> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<Integer> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(Integer value1, Integer value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(Integer value1, Integer value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andSortcodeIsNull() {
            addCriterion("sortcode is null");
            return (Criteria) this;
        }

        public Criteria andSortcodeIsNotNull() {
            addCriterion("sortcode is not null");
            return (Criteria) this;
        }

        public Criteria andSortcodeEqualTo(String value) {
            addCriterion("sortcode =", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeNotEqualTo(String value) {
            addCriterion("sortcode <>", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeGreaterThan(String value) {
            addCriterion("sortcode >", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeGreaterThanOrEqualTo(String value) {
            addCriterion("sortcode >=", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeLessThan(String value) {
            addCriterion("sortcode <", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeLessThanOrEqualTo(String value) {
            addCriterion("sortcode <=", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeLike(String value) {
            addCriterion("sortcode like", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeNotLike(String value) {
            addCriterion("sortcode not like", value, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeIn(List<String> values) {
            addCriterion("sortcode in", values, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeNotIn(List<String> values) {
            addCriterion("sortcode not in", values, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeBetween(String value1, String value2) {
            addCriterion("sortcode between", value1, value2, "sortcode");
            return (Criteria) this;
        }

        public Criteria andSortcodeNotBetween(String value1, String value2) {
            addCriterion("sortcode not between", value1, value2, "sortcode");
            return (Criteria) this;
        }

        public Criteria andConfirmIsNull() {
            addCriterion("confirm is null");
            return (Criteria) this;
        }

        public Criteria andConfirmIsNotNull() {
            addCriterion("confirm is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmEqualTo(String value) {
            addCriterion("confirm =", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmNotEqualTo(String value) {
            addCriterion("confirm <>", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmGreaterThan(String value) {
            addCriterion("confirm >", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmGreaterThanOrEqualTo(String value) {
            addCriterion("confirm >=", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmLessThan(String value) {
            addCriterion("confirm <", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmLessThanOrEqualTo(String value) {
            addCriterion("confirm <=", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmLike(String value) {
            addCriterion("confirm like", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmNotLike(String value) {
            addCriterion("confirm not like", value, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmIn(List<String> values) {
            addCriterion("confirm in", values, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmNotIn(List<String> values) {
            addCriterion("confirm not in", values, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmBetween(String value1, String value2) {
            addCriterion("confirm between", value1, value2, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmNotBetween(String value1, String value2) {
            addCriterion("confirm not between", value1, value2, "confirm");
            return (Criteria) this;
        }

        public Criteria andConfirmUidIsNull() {
            addCriterion("confirm_uid is null");
            return (Criteria) this;
        }

        public Criteria andConfirmUidIsNotNull() {
            addCriterion("confirm_uid is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmUidEqualTo(Long value) {
            addCriterion("confirm_uid =", value, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidNotEqualTo(Long value) {
            addCriterion("confirm_uid <>", value, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidGreaterThan(Long value) {
            addCriterion("confirm_uid >", value, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidGreaterThanOrEqualTo(Long value) {
            addCriterion("confirm_uid >=", value, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidLessThan(Long value) {
            addCriterion("confirm_uid <", value, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidLessThanOrEqualTo(Long value) {
            addCriterion("confirm_uid <=", value, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidIn(List<Long> values) {
            addCriterion("confirm_uid in", values, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidNotIn(List<Long> values) {
            addCriterion("confirm_uid not in", values, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidBetween(Long value1, Long value2) {
            addCriterion("confirm_uid between", value1, value2, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmUidNotBetween(Long value1, Long value2) {
            addCriterion("confirm_uid not between", value1, value2, "confirmUid");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampIsNull() {
            addCriterion("confirm_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampIsNotNull() {
            addCriterion("confirm_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampEqualTo(Date value) {
            addCriterion("confirm_timestamp =", value, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampNotEqualTo(Date value) {
            addCriterion("confirm_timestamp <>", value, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampGreaterThan(Date value) {
            addCriterion("confirm_timestamp >", value, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("confirm_timestamp >=", value, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampLessThan(Date value) {
            addCriterion("confirm_timestamp <", value, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampLessThanOrEqualTo(Date value) {
            addCriterion("confirm_timestamp <=", value, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampIn(List<Date> values) {
            addCriterion("confirm_timestamp in", values, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampNotIn(List<Date> values) {
            addCriterion("confirm_timestamp not in", values, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampBetween(Date value1, Date value2) {
            addCriterion("confirm_timestamp between", value1, value2, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andConfirmTimestampNotBetween(Date value1, Date value2) {
            addCriterion("confirm_timestamp not between", value1, value2, "confirmTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampIsNull() {
            addCriterion("autocheck_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampIsNotNull() {
            addCriterion("autocheck_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampEqualTo(Date value) {
            addCriterion("autocheck_timestamp =", value, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampNotEqualTo(Date value) {
            addCriterion("autocheck_timestamp <>", value, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampGreaterThan(Date value) {
            addCriterion("autocheck_timestamp >", value, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("autocheck_timestamp >=", value, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampLessThan(Date value) {
            addCriterion("autocheck_timestamp <", value, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampLessThanOrEqualTo(Date value) {
            addCriterion("autocheck_timestamp <=", value, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampIn(List<Date> values) {
            addCriterion("autocheck_timestamp in", values, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampNotIn(List<Date> values) {
            addCriterion("autocheck_timestamp not in", values, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampBetween(Date value1, Date value2) {
            addCriterion("autocheck_timestamp between", value1, value2, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andAutocheckTimestampNotBetween(Date value1, Date value2) {
            addCriterion("autocheck_timestamp not between", value1, value2, "autocheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampIsNull() {
            addCriterion("manualcheck_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampIsNotNull() {
            addCriterion("manualcheck_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampEqualTo(Date value) {
            addCriterion("manualcheck_timestamp =", value, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampNotEqualTo(Date value) {
            addCriterion("manualcheck_timestamp <>", value, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampGreaterThan(Date value) {
            addCriterion("manualcheck_timestamp >", value, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("manualcheck_timestamp >=", value, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampLessThan(Date value) {
            addCriterion("manualcheck_timestamp <", value, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampLessThanOrEqualTo(Date value) {
            addCriterion("manualcheck_timestamp <=", value, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampIn(List<Date> values) {
            addCriterion("manualcheck_timestamp in", values, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampNotIn(List<Date> values) {
            addCriterion("manualcheck_timestamp not in", values, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampBetween(Date value1, Date value2) {
            addCriterion("manualcheck_timestamp between", value1, value2, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andManualcheckTimestampNotBetween(Date value1, Date value2) {
            addCriterion("manualcheck_timestamp not between", value1, value2, "manualcheckTimestamp");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidIsNull() {
            addCriterion("manualcheck_uid is null");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidIsNotNull() {
            addCriterion("manualcheck_uid is not null");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidEqualTo(Long value) {
            addCriterion("manualcheck_uid =", value, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidNotEqualTo(Long value) {
            addCriterion("manualcheck_uid <>", value, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidGreaterThan(Long value) {
            addCriterion("manualcheck_uid >", value, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidGreaterThanOrEqualTo(Long value) {
            addCriterion("manualcheck_uid >=", value, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidLessThan(Long value) {
            addCriterion("manualcheck_uid <", value, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidLessThanOrEqualTo(Long value) {
            addCriterion("manualcheck_uid <=", value, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidIn(List<Long> values) {
            addCriterion("manualcheck_uid in", values, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidNotIn(List<Long> values) {
            addCriterion("manualcheck_uid not in", values, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidBetween(Long value1, Long value2) {
            addCriterion("manualcheck_uid between", value1, value2, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andManualcheckUidNotBetween(Long value1, Long value2) {
            addCriterion("manualcheck_uid not between", value1, value2, "manualcheckUid");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeIsNull() {
            addCriterion("newfeatcode is null");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeIsNotNull() {
            addCriterion("newfeatcode is not null");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeEqualTo(Integer value) {
            addCriterion("newfeatcode =", value, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeNotEqualTo(Integer value) {
            addCriterion("newfeatcode <>", value, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeGreaterThan(Integer value) {
            addCriterion("newfeatcode >", value, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("newfeatcode >=", value, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeLessThan(Integer value) {
            addCriterion("newfeatcode <", value, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeLessThanOrEqualTo(Integer value) {
            addCriterion("newfeatcode <=", value, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeIn(List<Integer> values) {
            addCriterion("newfeatcode in", values, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeNotIn(List<Integer> values) {
            addCriterion("newfeatcode not in", values, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeBetween(Integer value1, Integer value2) {
            addCriterion("newfeatcode between", value1, value2, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewfeatcodeNotBetween(Integer value1, Integer value2) {
            addCriterion("newfeatcode not between", value1, value2, "newfeatcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeIsNull() {
            addCriterion("newsortcode is null");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeIsNotNull() {
            addCriterion("newsortcode is not null");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeEqualTo(String value) {
            addCriterion("newsortcode =", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeNotEqualTo(String value) {
            addCriterion("newsortcode <>", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeGreaterThan(String value) {
            addCriterion("newsortcode >", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeGreaterThanOrEqualTo(String value) {
            addCriterion("newsortcode >=", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeLessThan(String value) {
            addCriterion("newsortcode <", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeLessThanOrEqualTo(String value) {
            addCriterion("newsortcode <=", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeLike(String value) {
            addCriterion("newsortcode like", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeNotLike(String value) {
            addCriterion("newsortcode not like", value, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeIn(List<String> values) {
            addCriterion("newsortcode in", values, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeNotIn(List<String> values) {
            addCriterion("newsortcode not in", values, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeBetween(String value1, String value2) {
            addCriterion("newsortcode between", value1, value2, "newsortcode");
            return (Criteria) this;
        }

        public Criteria andNewsortcodeNotBetween(String value1, String value2) {
            addCriterion("newsortcode not between", value1, value2, "newsortcode");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}