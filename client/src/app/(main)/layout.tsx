"use client";
import { useRouter } from "next/navigation";
import { MainLayout } from "./_components/main-layout";
import PermissionGuard from "@/components/permission-guard";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { LogInIcon, RefreshCwIcon } from "lucide-react";
import Image from "next/image";
import LoadingImage from "@/assets/loading.gif";
import { getQueryClient } from "../get-query-client";

export default function Layout({ children }: { children: React.ReactNode }) {
  const router = useRouter();

  return (
    <PermissionGuard
      required={["VIEW_MAIN_DASHBOARD"]}
      unauthorizedFallback={() => (
        <div className="flex justify-center items-center min-h-screen">
          <Card className="my-auto mx-auto max-w-screen-sm w-full">
            <CardHeader>
              <CardTitle>
                <span>Unauthorized</span>
              </CardTitle>
            </CardHeader>
            <CardContent>
              <p>
                You are not authorized to view this page. Please sign in to
                continue.
              </p>
            </CardContent>
            <CardFooter className="space-x-1 justify-end">
              <Button
                onClick={() => {
                  const queryClient = getQueryClient();
                  queryClient.resetQueries({
                    queryKey: ["current-user-permissions"],
                  });
                  router.refresh();
                }}
                variant={"outline"}
              >
                <RefreshCwIcon />
                Refresh
              </Button>
              <Button onClick={() => router.push("/sign-in")}>
                <LogInIcon />
                Sign In
              </Button>
            </CardFooter>
          </Card>
        </div>
      )}
      loadingFallback={() => (
        <div className="w-screen h-screen flex justify-center items-center">
          <Image
            src={LoadingImage}
            alt="loading"
            width={200}
            height={200}
            loading="eager"
          />
        </div>
      )}
    >
      <MainLayout>{children}</MainLayout>;
    </PermissionGuard>
  );
}
