import { HouseholdDetailsCard } from "./_components/household-details-card";

type Params = Promise<{ householdId: string }>;

export default async function Page({ params }: { params: Params }) {
  const { householdId } = await params;

  return <HouseholdDetailsCard householdId={householdId} />;
}
