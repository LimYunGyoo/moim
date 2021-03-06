package ac.moim.common.entity;

import ac.moim.study.entity.Study;
import ac.moim.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by SONG_HOHOON on 2016-12-22.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"studyList", "userList"})
@Entity
@Table(name = "city")
public class City extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8229685397241049006L;

	@Id
	@Column(name = "code")
	private Integer code;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private State stateId;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	private List<Study> studyList;

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	private List<User> userList;
}
