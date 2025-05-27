package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.services.DetectorDeSpam;
import org.springframework.stereotype.Service;

@Service("dummyDetectorDeSpam")
public class DummyDetectorDeSpamImpl implements DetectorDeSpam {
    @Override
    public boolean esSpam(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return true;
        }
        return texto.toLowerCase().contains("spam");
    }
}