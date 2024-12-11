import { HouseholdDetailsCard } from "./_components/household-details-card";

type Params = Promise<{ householdId: string }>;

export default function Page({ params }: { params: Params }) {
  return <HouseholdDetailsCard householdId={params.householdId} />;
}
