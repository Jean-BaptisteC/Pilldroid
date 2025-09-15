package net.foucry.pilldroid.utils;

import static net.foucry.pilldroid.utils.Utils.intRandomExclusive;

import net.foucry.pilldroid.dao.PrescriptionsDAO;
import net.foucry.pilldroid.models.Prescription;

import java.util.Date;

public class DemoMedicine {
    public static void generateMedicine(PrescriptionsDAO prescriptionsDemo) {

        if (prescriptionsDemo.getMedicCount() == 0) {
            final int min_stock = 5;
            final int max_stock = 50;
            final int min_take = 0;
            final int max_take = 3;

            for (int i = 1; i < 9; i++) {
                Prescription prescription = new Prescription();
                prescription.setName("Medicament test " + i);
                prescription.setCip13("340093000001" + i);
                prescription.setCis("6000001" + i);
                prescription.setAdministration_mode("oral");
                prescription.setPresentation("plaquette(s) thermoformée(s) PVC PVDC aluminium de 10 comprimé(s)");
                prescription.setStock((float) intRandomExclusive(min_stock, max_stock));
                prescription.setTake((float) intRandomExclusive(min_take, max_take));
                prescription.setWarning(14);
                prescription.setAlert(7);
                prescription.setLast_update(UtilDate.dateAtNoon(new Date()).getTime());

                prescriptionsDemo.insert(prescription);
            }
        }
    }
}
