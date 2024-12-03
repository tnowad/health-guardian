import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

interface MedicalInfo {
  diagnoses: string[];
  allergies: string[];
  history: string[];
}

const mockMedicalInfo: MedicalInfo = {
  diagnoses: ["Hypertension", "Type 2 Diabetes"],
  allergies: ["Penicillin", "Peanuts"],
  history: ["Appendectomy (2015)", "Fractured left arm (2010)"],
};

export default function MedicalStatus() {
  return (
    <div className="grid gap-6 md:grid-cols-3">
      <Card>
        <CardHeader>
          <CardTitle>Diagnoses</CardTitle>
        </CardHeader>
        <CardContent>
          <ul className="list-disc pl-5">
            {mockMedicalInfo.diagnoses.map((diagnosis, index) => (
              <li key={index}>{diagnosis}</li>
            ))}
          </ul>
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle>Allergies</CardTitle>
        </CardHeader>
        <CardContent>
          <ul className="list-disc pl-5">
            {mockMedicalInfo.allergies.map((allergy, index) => (
              <li key={index}>{allergy}</li>
            ))}
          </ul>
        </CardContent>
      </Card>
      <Card>
        <CardHeader>
          <CardTitle>History</CardTitle>
        </CardHeader>
        <CardContent>
          <ul className="list-disc pl-5">
            {mockMedicalInfo.history.map((item, index) => (
              <li key={index}>{item}</li>
            ))}
          </ul>
        </CardContent>
      </Card>
    </div>
  );
}
