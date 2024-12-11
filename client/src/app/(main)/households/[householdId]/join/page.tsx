import { Button } from "@/components/ui/button";
import Link from "next/link";

type Params = Promise<{ householdId: string }>;

export default async function Page({ params }: { params: Params }) {
  const { householdId } = await params;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-2xl font-bold mb-4">Join Household</h1>
      <p className="mb-4">You've been invited to join the household:</p>
      <div className="space-y-4">
        <p>Do you want to join</p>
        <div className="space-x-4">
          <Button>Yes, Join Household</Button>
          <Button variant="outline">
            <Link href="/households">No, Decline Invitation</Link>
          </Button>
        </div>
      </div>
    </div>
  );
}
