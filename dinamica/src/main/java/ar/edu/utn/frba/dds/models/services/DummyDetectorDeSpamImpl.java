package ar.edu.utn.frba.dds.models.services;

public class DummyDetectorDeSpamImpl implements DetectorDeSpam {
    @Override
    public boolean esSpam(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return true; // Considerar texto vacío como spam para este dummy
        }
        // Lógica de detección de spam muy básica (ejemplo)
        // En un caso real, aquí iría la implementación de TF-IDF u otro servicio. [cite: 131, 132]
        return texto.toLowerCase().contains("spam ejemplo");
    }
}