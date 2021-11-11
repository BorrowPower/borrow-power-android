package ng.borrowpower.android.Models;

public class Discos {
    String name,slug;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Discos(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return name;
    }
}
