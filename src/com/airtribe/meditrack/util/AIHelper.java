
package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.enums.Specialization;

//SIMPLE RULE-BASED HELPER FOR SUGGESTING SPECIALIZATION BASED ON SYMPTOMS.THIS CAN BE EXTENDED TO CALL AN LLM API IF NEEDED FOR MORE COMPLEX RECOMMENDATIONS (DOCTOR BY SYMPTOM OR APPOINTMENT SLOTS).
public class AIHelper {
    public static Specialization recommend(String symptom) {
        symptom = symptom.toLowerCase();
        if (symptom.contains("skin"))
            return Specialization.DERMATOLOGY;
        if (symptom.contains("heart"))
            return Specialization.CARDIOLOGY;
        if (symptom.contains("bone"))
            return Specialization.ORTHOPEDICS;
        if (symptom.contains("brain"))
            return Specialization.NEUROLOGY;
        return Specialization.GENERAL;
    }
}
