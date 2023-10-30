package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "specialization")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "brief_description", nullable = false, length = 64)
    private String briefDescription;

    @Column(name = "full_description", nullable = false, length = 512)
    private String fullDescription;

    @Column(name = "code", nullable = false, length = 64)
    private String code;


    public Specialization() {
    }

    public Specialization(String briefDescription, String fullDescription, String code) {
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "id=" + id +
                ", briefDescription='" + briefDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Specialization that = (Specialization) object;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
