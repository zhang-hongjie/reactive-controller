package com.example.config.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

@Entity
@RevisionEntity
@Table(schema = "public", name = "revinfo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevinfoEntity {
	@Id
	@RevisionNumber
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="hibernate_sequence")
	@SequenceGenerator(name="hibernate_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    @Column
	private int rev;

	@RevisionTimestamp
    @Column(nullable = true)
	private long revtstmp;
}