package cn.emg.poibatcheditor.pojo;

import java.util.ArrayList;
import java.util.List;

public class AdminCodeModelExample {
    protected String orderByClause;

    protected boolean distinct;
    
    protected Integer limit;
    
    public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	protected Integer offset;

    protected List<Criteria> oredCriteria;

    public AdminCodeModelExample() {
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

        public Criteria andAdaidIsNull() {
            addCriterion("adaid is null");
            return (Criteria) this;
        }

        public Criteria andAdaidIsNotNull() {
            addCriterion("adaid is not null");
            return (Criteria) this;
        }

        public Criteria andAdaidEqualTo(Integer value) {
            addCriterion("adaid =", value, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidNotEqualTo(Integer value) {
            addCriterion("adaid <>", value, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidGreaterThan(Integer value) {
            addCriterion("adaid >", value, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidGreaterThanOrEqualTo(Integer value) {
            addCriterion("adaid >=", value, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidLessThan(Integer value) {
            addCriterion("adaid <", value, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidLessThanOrEqualTo(Integer value) {
            addCriterion("adaid <=", value, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidIn(List<Integer> values) {
            addCriterion("adaid in", values, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidNotIn(List<Integer> values) {
            addCriterion("adaid not in", values, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidBetween(Integer value1, Integer value2) {
            addCriterion("adaid between", value1, value2, "adaid");
            return (Criteria) this;
        }

        public Criteria andAdaidNotBetween(Integer value1, Integer value2) {
            addCriterion("adaid not between", value1, value2, "adaid");
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

        public Criteria andNamepIsNull() {
            addCriterion("namep is null");
            return (Criteria) this;
        }

        public Criteria andNamepIsNotNull() {
            addCriterion("namep is not null");
            return (Criteria) this;
        }

        public Criteria andNamepEqualTo(String value) {
            addCriterion("namep =", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepNotEqualTo(String value) {
            addCriterion("namep <>", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepGreaterThan(String value) {
            addCriterion("namep >", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepGreaterThanOrEqualTo(String value) {
            addCriterion("namep >=", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepLessThan(String value) {
            addCriterion("namep <", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepLessThanOrEqualTo(String value) {
            addCriterion("namep <=", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepLike(String value) {
            addCriterion("namep like", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepNotLike(String value) {
            addCriterion("namep not like", value, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepIn(List<String> values) {
            addCriterion("namep in", values, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepNotIn(List<String> values) {
            addCriterion("namep not in", values, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepBetween(String value1, String value2) {
            addCriterion("namep between", value1, value2, "namep");
            return (Criteria) this;
        }

        public Criteria andNamepNotBetween(String value1, String value2) {
            addCriterion("namep not between", value1, value2, "namep");
            return (Criteria) this;
        }

        public Criteria andNameeIsNull() {
            addCriterion("namee is null");
            return (Criteria) this;
        }

        public Criteria andNameeIsNotNull() {
            addCriterion("namee is not null");
            return (Criteria) this;
        }

        public Criteria andNameeEqualTo(String value) {
            addCriterion("namee =", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeNotEqualTo(String value) {
            addCriterion("namee <>", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeGreaterThan(String value) {
            addCriterion("namee >", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeGreaterThanOrEqualTo(String value) {
            addCriterion("namee >=", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeLessThan(String value) {
            addCriterion("namee <", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeLessThanOrEqualTo(String value) {
            addCriterion("namee <=", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeLike(String value) {
            addCriterion("namee like", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeNotLike(String value) {
            addCriterion("namee not like", value, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeIn(List<String> values) {
            addCriterion("namee in", values, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeNotIn(List<String> values) {
            addCriterion("namee not in", values, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeBetween(String value1, String value2) {
            addCriterion("namee between", value1, value2, "namee");
            return (Criteria) this;
        }

        public Criteria andNameeNotBetween(String value1, String value2) {
            addCriterion("namee not between", value1, value2, "namee");
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

        public Criteria andAreacodeIsNull() {
            addCriterion("areacode is null");
            return (Criteria) this;
        }

        public Criteria andAreacodeIsNotNull() {
            addCriterion("areacode is not null");
            return (Criteria) this;
        }

        public Criteria andAreacodeEqualTo(String value) {
            addCriterion("areacode =", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotEqualTo(String value) {
            addCriterion("areacode <>", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThan(String value) {
            addCriterion("areacode >", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeGreaterThanOrEqualTo(String value) {
            addCriterion("areacode >=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThan(String value) {
            addCriterion("areacode <", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLessThanOrEqualTo(String value) {
            addCriterion("areacode <=", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeLike(String value) {
            addCriterion("areacode like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotLike(String value) {
            addCriterion("areacode not like", value, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeIn(List<String> values) {
            addCriterion("areacode in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotIn(List<String> values) {
            addCriterion("areacode not in", values, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeBetween(String value1, String value2) {
            addCriterion("areacode between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andAreacodeNotBetween(String value1, String value2) {
            addCriterion("areacode not between", value1, value2, "areacode");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNull() {
            addCriterion("postcode is null");
            return (Criteria) this;
        }

        public Criteria andPostcodeIsNotNull() {
            addCriterion("postcode is not null");
            return (Criteria) this;
        }

        public Criteria andPostcodeEqualTo(String value) {
            addCriterion("postcode =", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotEqualTo(String value) {
            addCriterion("postcode <>", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThan(String value) {
            addCriterion("postcode >", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeGreaterThanOrEqualTo(String value) {
            addCriterion("postcode >=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThan(String value) {
            addCriterion("postcode <", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLessThanOrEqualTo(String value) {
            addCriterion("postcode <=", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeLike(String value) {
            addCriterion("postcode like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotLike(String value) {
            addCriterion("postcode not like", value, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeIn(List<String> values) {
            addCriterion("postcode in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotIn(List<String> values) {
            addCriterion("postcode not in", values, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeBetween(String value1, String value2) {
            addCriterion("postcode between", value1, value2, "postcode");
            return (Criteria) this;
        }

        public Criteria andPostcodeNotBetween(String value1, String value2) {
            addCriterion("postcode not between", value1, value2, "postcode");
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

        public Criteria andFeatcodeEqualTo(Integer value) {
            addCriterion("featcode =", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeNotEqualTo(Integer value) {
            addCriterion("featcode <>", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeGreaterThan(Integer value) {
            addCriterion("featcode >", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("featcode >=", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeLessThan(Integer value) {
            addCriterion("featcode <", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeLessThanOrEqualTo(Integer value) {
            addCriterion("featcode <=", value, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeIn(List<Integer> values) {
            addCriterion("featcode in", values, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeNotIn(List<Integer> values) {
            addCriterion("featcode not in", values, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeBetween(Integer value1, Integer value2) {
            addCriterion("featcode between", value1, value2, "featcode");
            return (Criteria) this;
        }

        public Criteria andFeatcodeNotBetween(Integer value1, Integer value2) {
            addCriterion("featcode not between", value1, value2, "featcode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNull() {
            addCriterion("citycode is null");
            return (Criteria) this;
        }

        public Criteria andCitycodeIsNotNull() {
            addCriterion("citycode is not null");
            return (Criteria) this;
        }

        public Criteria andCitycodeEqualTo(Integer value) {
            addCriterion("citycode =", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotEqualTo(Integer value) {
            addCriterion("citycode <>", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThan(Integer value) {
            addCriterion("citycode >", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("citycode >=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThan(Integer value) {
            addCriterion("citycode <", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeLessThanOrEqualTo(Integer value) {
            addCriterion("citycode <=", value, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeIn(List<Integer> values) {
            addCriterion("citycode in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotIn(List<Integer> values) {
            addCriterion("citycode not in", values, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeBetween(Integer value1, Integer value2) {
            addCriterion("citycode between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andCitycodeNotBetween(Integer value1, Integer value2) {
            addCriterion("citycode not between", value1, value2, "citycode");
            return (Criteria) this;
        }

        public Criteria andRankIsNull() {
            addCriterion("rank is null");
            return (Criteria) this;
        }

        public Criteria andRankIsNotNull() {
            addCriterion("rank is not null");
            return (Criteria) this;
        }

        public Criteria andRankEqualTo(Integer value) {
            addCriterion("rank =", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotEqualTo(Integer value) {
            addCriterion("rank <>", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThan(Integer value) {
            addCriterion("rank >", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankGreaterThanOrEqualTo(Integer value) {
            addCriterion("rank >=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThan(Integer value) {
            addCriterion("rank <", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankLessThanOrEqualTo(Integer value) {
            addCriterion("rank <=", value, "rank");
            return (Criteria) this;
        }

        public Criteria andRankIn(List<Integer> values) {
            addCriterion("rank in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotIn(List<Integer> values) {
            addCriterion("rank not in", values, "rank");
            return (Criteria) this;
        }

        public Criteria andRankBetween(Integer value1, Integer value2) {
            addCriterion("rank between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andRankNotBetween(Integer value1, Integer value2) {
            addCriterion("rank not between", value1, value2, "rank");
            return (Criteria) this;
        }

        public Criteria andAliascIsNull() {
            addCriterion("aliasc is null");
            return (Criteria) this;
        }

        public Criteria andAliascIsNotNull() {
            addCriterion("aliasc is not null");
            return (Criteria) this;
        }

        public Criteria andAliascEqualTo(String value) {
            addCriterion("aliasc =", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascNotEqualTo(String value) {
            addCriterion("aliasc <>", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascGreaterThan(String value) {
            addCriterion("aliasc >", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascGreaterThanOrEqualTo(String value) {
            addCriterion("aliasc >=", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascLessThan(String value) {
            addCriterion("aliasc <", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascLessThanOrEqualTo(String value) {
            addCriterion("aliasc <=", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascLike(String value) {
            addCriterion("aliasc like", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascNotLike(String value) {
            addCriterion("aliasc not like", value, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascIn(List<String> values) {
            addCriterion("aliasc in", values, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascNotIn(List<String> values) {
            addCriterion("aliasc not in", values, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascBetween(String value1, String value2) {
            addCriterion("aliasc between", value1, value2, "aliasc");
            return (Criteria) this;
        }

        public Criteria andAliascNotBetween(String value1, String value2) {
            addCriterion("aliasc not between", value1, value2, "aliasc");
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