package ru.ddc.schedulestoring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "specialization")
public class Specialization {
    public static final String NULL_OR_BLANK_ARGUMENT_MESSAGE = "Не указан один из аргументов";

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "brief_description", nullable = false, length = 64)
    private String briefDescription;

    @Getter
    @Setter
    @Column(name = "full_description", nullable = false, length = 512)
    private String fullDescription;

    @Getter
    @Setter
    @Column(name = "code", unique = true, nullable = false, length = 64)
    private String code;


    public Specialization() {
    }

    public Specialization(String briefDescription, String fullDescription, String code) {
        if (briefDescription == null || fullDescription == null || code == null ||
                briefDescription.isBlank() || fullDescription.isBlank() || code.isBlank() ||
                briefDescription.length() < 5 || fullDescription.length() < 5 || code.length() < 2) {
            throw new IllegalArgumentException(NULL_OR_BLANK_ARGUMENT_MESSAGE);
        } else {
            this.briefDescription = briefDescription;
            this.fullDescription = fullDescription;
            this.code = code;
        }
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Specialization that = (Specialization) object;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    // TODO удалить метод toString
    @Override
    public String toString() {
        return "Specialization{" +
                "id=" + id +
                ", briefDescription='" + briefDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

/*
 *                  const   null            insert  update                  init    change
 *  field           arg     able    unique  able    able    setter  getter  method  method
 *  --------------------------------------------------------------------------------------
 *  --- THIS -----------------------------------------------------------------------------
 *  --------------------------------------------------------------------------------------
 *  id              no      no      yes     yes     yes     no      yes     ---     ---
 *  briefDescr      yes     no      no      yes     yes     yes     yes     const   setter
 *  fullDescr       yes     no      no      yes     yes     yes     yes     const   setter
 *  code            yes     no      yes     yes     yes     yes     yes     const   setter
 *  --------------------------------------------------------------------------------------
 *
 **/
