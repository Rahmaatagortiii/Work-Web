package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Image;
import com.wellbeignatwork.backend.exceptions.Collaboration.ResourceNotFoundException;
import com.wellbeignatwork.backend.repository.Collaboration.ICollaboration;
import com.wellbeignatwork.backend.repository.Collaboration.ImageRepo;
import com.wellbeignatwork.backend.repository.Collaboration.OfferRepository;
import com.wellbeignatwork.backend.repository.Collaboration.IPublicity;
import com.wellbeignatwork.backend.repository.User.UserRepository;

import com.wellbeignatwork.backend.entity.Collaboration.Collaboration;
import com.wellbeignatwork.backend.util.FirebaseStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class CollaborationService implements ICollaborationService {
	@Autowired
	OfferRepository OfferRepo;

	@Autowired
	ImageRepo imageRepo;

	@Autowired
	FirebaseStorage firebaseStorage;
	@Autowired
	ICollaboration CollaborationRepo;
	
	@Autowired
	IPublicity PublicityRepo;

	@Autowired
    UserRepository userRepo;


	@Override
	public List<Collaboration> retrieveAllCollaborations() {
		List<Collaboration> collaborations = (List<Collaboration>) CollaborationRepo.findAll();
		return collaborations;
	}

	@Override
	public void updateCollaboration(Collaboration c) {
		CollaborationRepo.save(c);
	}

	@Override
	public void deleteCollaboration(Long id) {
		CollaborationRepo.deleteById(id);

	}

	@Override
	public Collaboration addCollaboration(Collaboration c) {
		return CollaborationRepo.save(c);
	}

	@Override
	public Collaboration retrieveCollaboration(Long id) {
		Collaboration collaboration = CollaborationRepo.findById(id).orElse(null);
		return collaboration;
	}

	@Override
	public void uploadImageToCollabotration(MultipartFile img, Long idCollaboration) throws IOException {
		Collaboration collaboration = CollaborationRepo.findById(idCollaboration).orElse(null);
		if (collaboration == null) {
			throw new ResourceNotFoundException("Collaboration is not exist");
		}
		String name = firebaseStorage.uploadFile(img);
		Image image = new Image();
		image.setName("https://firebasestorage.googleapis.com/v0/b/"+firebaseStorage.getBUCKETNAME()+"/o/"+name+"?alt=media");
		imageRepo.save(image);
		collaboration.setImagesCollab(image);
		CollaborationRepo.save(collaboration);
	}
}
