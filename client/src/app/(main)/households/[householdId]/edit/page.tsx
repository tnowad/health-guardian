import { UpdateHouseholdForm } from "./_components/update-household-form";
type Params = Promise<{ householdId: string }>;

export default async function Page({ params }: { params: Params }) {
  const { householdId } = await params;

  return <UpdateHouseholdForm householdId={householdId} />;
}
