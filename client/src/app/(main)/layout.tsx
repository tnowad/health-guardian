"use client";
import { useRouter } from "next/navigation";
import { MainLayout } from "./_components/main-layout";
import PermissionGuard from "@/components/permission-guard";

export default function Layout({ children }: { children: React.ReactNode }) {
  const router = useRouter();

  return (
    <PermissionGuard
      required={["VIEW_MAIN_DASHBOARD"]}
      onUnauthorized={() => router.push("/sign-in")}
      loadingFallback={() => (
        <div className="w-screen h-screen flex justify-center items-center">
          <img
            src="https://media0.giphy.com/media/3ov9jZ0V6gOO0oa98Y/200w.gif?cid=6c09b952fgyf5jmwu3w4dr9rtt3z5nt519rtfrbul66b6uvv"
            alt="loading"
            width="200px"
            height="200px"
          />
        </div>
      )}
    >
      <MainLayout>{children}</MainLayout>;
    </PermissionGuard>
  );
}
