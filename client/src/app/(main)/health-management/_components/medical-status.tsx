import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { createGetCurrentUserInformationQueryOptions } from "@/lib/apis/get-current-user-information.api";
import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import { createListAllergiesQueryOptions } from "@/lib/apis/list-allergies.api";
import { createListDiagnosticReportsQueryOptions } from "@/lib/apis/list-diagnostic-report.api";
import { createListFamilyHistoriesQueryOptions } from "@/lib/apis/(family-history)/list-family-histories.api";
export default function MedicalStatus() {

  const currentUserInformationQuery = useSuspenseQuery(
    createGetCurrentUserInformationQueryOptions(),
  );

  const listAllergiesQuery = useQuery(
    createListAllergiesQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );

  const listDiagnosesQuery = useQuery(
    createListDiagnosticReportsQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );

  const listHistoryQuery = useQuery(
    createListFamilyHistoriesQueryOptions({
      userId: currentUserInformationQuery.data?.userId,
    }),
  );

  const allergies = listAllergiesQuery.data?.content ?? [];

  const diagnoses = listDiagnosesQuery.data?.content ?? [];

  const histories = listHistoryQuery.data?.content ?? [];

  return (
    <div className="grid gap-6 md:grid-cols-3">
      <Card>
        <CardHeader>
          <CardTitle>Diagnoses</CardTitle>
        </CardHeader>
        <CardContent>
          <ul className="list-disc pl-5">
            {diagnoses.map((diagnosis) => (
              <li>{diagnosis.summary}</li>
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
            {allergies.map((allergy) => (
              <li >{allergy.allergyName}</li>
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
            {histories.map((history) => (
              <li >{history.description}</li>
            ))}
          </ul>
        </CardContent>
      </Card>
    </div>
  );
}
