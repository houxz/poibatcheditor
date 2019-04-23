package cn.emg.poibatcheditor.pojo;

public class AdminCodeModel {
    private Integer adaid;

    private String namec;

    private String namep;

    private String namee;

    private Integer owner;

    private String areacode;

    private String postcode;

    private Integer featcode;

    private Integer citycode;

    private Integer rank;

    private String aliasc;

    public Integer getAdaid() {
        return adaid;
    }

    public void setAdaid(Integer adaid) {
        this.adaid = adaid;
    }

    public String getNamec() {
        return namec;
    }

    public void setNamec(String namec) {
        this.namec = namec == null ? null : namec.trim();
    }

    public String getNamep() {
        return namep;
    }

    public void setNamep(String namep) {
        this.namep = namep == null ? null : namep.trim();
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee == null ? null : namee.trim();
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode == null ? null : areacode.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public Integer getFeatcode() {
        return featcode;
    }

    public void setFeatcode(Integer featcode) {
        this.featcode = featcode;
    }

    public Integer getCitycode() {
        return citycode;
    }

    public void setCitycode(Integer citycode) {
        this.citycode = citycode;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getAliasc() {
        return aliasc;
    }

    public void setAliasc(String aliasc) {
        this.aliasc = aliasc == null ? null : aliasc.trim();
    }
}