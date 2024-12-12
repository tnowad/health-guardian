import { JoinCard } from "./_components/join-card";

type Params = Promise<{ householdId: string }>;

export default async function Page({ params }: { params: Params }) {
  const { householdId } = await params;

  return <JoinCard householdId={householdId} />;
}
