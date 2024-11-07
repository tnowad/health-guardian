"use client";
import { ReactNode, useMemo } from "react";
import { createGetCurrentUserPermissionsQueryOptions } from "@/lib/apis/get-current-user-permissions.api";
import { useQuery } from "@tanstack/react-query";

type PermissionGuardProps = {
  required: string[];
  children: ReactNode;
  operator?: "AND" | "OR";
  onUnauthorized?: () => void;
  onLoading?: () => void;
  onErrored?: (error: Error) => void;
  unauthorizedFallback?: () => ReactNode;
  loadingFallback?: () => ReactNode;
  erroredFallback?: (error: Error) => ReactNode;
};

export default function PermissionGuard({
  required,
  children,
  operator = "AND",
  onUnauthorized,
  onLoading,
  onErrored,
  unauthorizedFallback,
  loadingFallback,
  erroredFallback,
}: PermissionGuardProps): JSX.Element | null {
  const {
    data: { items: permissions } = { items: [] },
    isLoading,
    error,
  } = useQuery(createGetCurrentUserPermissionsQueryOptions());

  const hasPermissions = useMemo(() => {
    if (permissions.length === 0) return false;
    return operator === "AND"
      ? required.every((perm) => permissions.includes(perm))
      : required.some((perm) => permissions.includes(perm));
  }, [permissions, required, operator]);

  if (isLoading) {
    if (loadingFallback) return <>{loadingFallback()}</>;
    onLoading?.();
    return null;
  }

  if (error) {
    if (erroredFallback) return <>{erroredFallback(error)}</>;
    onErrored?.(error);
    return null;
  }

  if (!hasPermissions) {
    if (unauthorizedFallback) return <>{unauthorizedFallback()}</>;
    onUnauthorized?.();
    return null;
  }

  return <>{children}</>;
}
