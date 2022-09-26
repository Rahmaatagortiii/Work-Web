package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Collaboration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ICollaborationService {
    List<Collaboration> retrieveAllCollaborations();

    void updateCollaboration(Collaboration c);

    void deleteCollaboration(Long id);

    Collaboration addCollaboration(Collaboration c);

    Collaboration retrieveCollaboration(Long id);

    void uploadImageToCollabotration(MultipartFile img, Long idCollaboration) throws IOException;
}
