package com.cmbccd.ulms.sys.domain;


import java.util.List;

public class Menu {
    private Integer id;

    private String name;

    private String intro;

    private String path;

    private Integer sort;

    private Integer pid;

    private String icon;

    private Short status;

    private Short useage;

    private Short menuType;

    private String resourse;

    private String component;

    private String system;

    private List<Menu> children;

    private MenuMeta meta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getUseage() {
        return useage;
    }

    public void setUseage(Short useage) {
        this.useage = useage;
    }

    public Short getMenuType() {
        return menuType;
    }

    public void setMenuType(Short menuType) {
        this.menuType = menuType;
    }

    public String getResourse() {
        return resourse;
    }

    public void setResourse(String resourse) {
        this.resourse = resourse == null ? null : resourse.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system == null ? null : system.trim();
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public MenuMeta getMeta() {
        return meta;
    }

    public void setMeta(MenuMeta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", path='" + path + '\'' +
                ", sort=" + sort +
                ", pid=" + pid +
                ", icon='" + icon + '\'' +
                ", status=" + status +
                ", useage=" + useage +
                ", menuType=" + menuType +
                ", resourse='" + resourse + '\'' +
                ", component='" + component + '\'' +
                ", system='" + system + '\'' +
                ", children=" + children +
                ", meta=" + meta +
                '}';
    }
}