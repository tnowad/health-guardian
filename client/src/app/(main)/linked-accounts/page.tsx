"use client";

import { useState } from "react";
import { Check, X, Plus, Link } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

interface LinkedAccount {
  id: string;
  provider: string;
  isLinked: boolean;
}

const initialAccounts: LinkedAccount[] = [
  { id: "1", provider: "Google", isLinked: true },
  { id: "2", provider: "Facebook", isLinked: false },
  { id: "3", provider: "Twitter", isLinked: true },
];

export default function LinkedAccountsScreen() {
  const [accounts, setAccounts] = useState<LinkedAccount[]>(initialAccounts);
  const [isLinkModalOpen, setIsLinkModalOpen] = useState(false);
  const [selectedProvider, setSelectedProvider] = useState("");

  const unlinkAccount = (id: string) => {
    setAccounts(
      accounts.map((account) =>
        account.id === id ? { ...account, isLinked: false } : account
      )
    );
  };

  const linkAccount = () => {
    if (selectedProvider) {
      const existingAccount = accounts.find(
        (account) => account.provider === selectedProvider
      );
      if (existingAccount) {
        setAccounts(
          accounts.map((account) =>
            account.id === existingAccount.id
              ? { ...account, isLinked: true }
              : account
          )
        );
      } else {
        setAccounts([
          ...accounts,
          {
            id: Date.now().toString(),
            provider: selectedProvider,
            isLinked: true,
          },
        ]);
      }
      setSelectedProvider("");
      setIsLinkModalOpen(false);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl font-bold">Linked Accounts</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="space-y-4">
            {accounts.map((account) => (
              <div
                key={account.id}
                className="flex items-center justify-between p-2 border rounded"
              >
                <div className="flex items-center">
                  {account.isLinked ? (
                    <Check className="h-5 w-5 text-green-500 mr-2" />
                  ) : (
                    <X className="h-5 w-5 text-red-500 mr-2" />
                  )}
                  <span className="font-medium">{account.provider}</span>
                </div>
                {account.isLinked && (
                  <Button
                    variant="outline"
                    size="sm"
                    onClick={() => unlinkAccount(account.id)}
                  >
                    Unlink
                  </Button>
                )}
              </div>
            ))}
          </div>
          <Dialog open={isLinkModalOpen} onOpenChange={setIsLinkModalOpen}>
            <DialogTrigger asChild>
              <Button className="mt-4">
                <Plus className="mr-2 h-4 w-4" /> Link Account
              </Button>
            </DialogTrigger>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Link Account</DialogTitle>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <Select
                  value={selectedProvider}
                  onValueChange={setSelectedProvider}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Select Provider" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="Google">Google</SelectItem>
                    <SelectItem value="Facebook">Facebook</SelectItem>
                    <SelectItem value="Twitter">Twitter</SelectItem>
                    <SelectItem value="LinkedIn">LinkedIn</SelectItem>
                  </SelectContent>
                </Select>
                <div className="flex justify-end space-x-2">
                  <Button
                    variant="outline"
                    onClick={() => setIsLinkModalOpen(false)}
                  >
                    Cancel
                  </Button>
                  <Button onClick={linkAccount}>
                    <Link className="mr-2 h-4 w-4" /> Link Account
                  </Button>
                </div>
              </div>
            </DialogContent>
          </Dialog>
        </CardContent>
      </Card>
    </div>
  );
}
