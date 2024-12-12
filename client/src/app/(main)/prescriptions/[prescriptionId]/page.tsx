import { PrescriptionDetailsCard } from "./_components/prescription-details-card";

type Params = Promise<{ prescriptionId: string }>;
export default async function Page({ params }: { params: Params }) {
  const { prescriptionId } = await params;
  return <PrescriptionDetailsCard prescriptionId={prescriptionId} />;
}
