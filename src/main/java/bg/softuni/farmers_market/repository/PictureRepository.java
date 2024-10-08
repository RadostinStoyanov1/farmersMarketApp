package bg.softuni.farmers_market.repository;

import bg.softuni.farmers_market.model.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
     public List<PictureEntity> findAllByOfferId(Long id);

     @Transactional
     @Modifying
     public void deleteByOfferId(Long id);
}
