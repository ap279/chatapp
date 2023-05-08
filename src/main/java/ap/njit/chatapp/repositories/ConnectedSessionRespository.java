package ap.njit.chatapp.repositories;

import ap.njit.chatapp.entities.ConnectedSession;
import org.springframework.data.repository.CrudRepository;

public interface ConnectedSessionRespository extends CrudRepository<ConnectedSession, Integer> {
}
