import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Heart, Shield, Users, BookOpen, Bell, Smartphone } from "lucide-react";

export default function LandingPage() {
  return (
    <div className="flex flex-col min-h-screen">
      <header className="px-4 lg:px-6 h-16 flex items-center">
        <Link className="flex items-center justify-center" href="#">
          <Heart className="h-6 w-6 text-green-600" />
          <span className="ml-2 text-2xl font-bold text-gray-900">
            Health Guardian
          </span>
        </Link>
        <nav className="ml-auto flex gap-4 sm:gap-6">
          <Link
            className="text-sm font-medium hover:underline underline-offset-4"
            href="#features"
          >
            Features
          </Link>
          <Link
            className="text-sm font-medium hover:underline underline-offset-4"
            href="#how-it-works"
          >
            How It Works
          </Link>
          <Link
            className="text-sm font-medium hover:underline underline-offset-4"
            href="#pricing"
          >
            Pricing
          </Link>
        </nav>
      </header>
      <main className="flex-1">
        <section className="w-full py-12 md:py-24 lg:py-32 xl:py-48 bg-green-50">
          <div className="container mx-auto px-4 md:px-6">
            <div className="flex flex-col items-center space-y-4 text-center">
              <div className="space-y-2">
                <h1 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl lg:text-6xl/none">
                  Your Family's Health, All in One Place
                </h1>
                <p className="mx-auto max-w-[700px] text-gray-600 md:text-xl">
                  Health Guardian: The ultimate medical record book for
                  families. Keep track of your loved ones' health history,
                  appointments, and medications with ease.
                </p>
              </div>
              <div className="space-x-4">
                <Button className="bg-green-600 hover:bg-green-700" asChild>
                  <Link href="/sign-in">Get Started</Link>
                </Button>
                <Button variant="outline">Learn More</Button>
              </div>
            </div>
          </div>
        </section>
        <section id="features" className="w-full py-12 md:py-24 lg:py-32">
          <div className="container mx-auto px-4 md:px-6">
            <h2 className="text-3xl font-bold tracking-tighter sm:text-5xl text-center mb-12">
              Key Features
            </h2>
            <div className="grid gap-8 sm:grid-cols-2 lg:grid-cols-3">
              <div className="flex flex-col items-center text-center">
                <Shield className="h-12 w-12 text-green-600 mb-4" />
                <h3 className="text-xl font-bold mb-2">Secure Storage</h3>
                <p className="text-gray-600">
                  Your family's medical data is encrypted and securely stored.
                </p>
              </div>
              <div className="flex flex-col items-center text-center">
                <Users className="h-12 w-12 text-green-600 mb-4" />
                <h3 className="text-xl font-bold mb-2">Family Sharing</h3>
                <p className="text-gray-600">
                  Share records with family members and caregivers as needed.
                </p>
              </div>
              <div className="flex flex-col items-center text-center">
                <BookOpen className="h-12 w-12 text-green-600 mb-4" />
                <h3 className="text-xl font-bold mb-2">
                  Comprehensive Records
                </h3>
                <p className="text-gray-600">
                  Store medical history, allergies, medications, and more.
                </p>
              </div>
              <div className="flex flex-col items-center text-center">
                <Bell className="h-12 w-12 text-green-600 mb-4" />
                <h3 className="text-xl font-bold mb-2">
                  Appointment Reminders
                </h3>
                <p className="text-gray-600">
                  Never miss a doctor's appointment with built-in reminders.
                </p>
              </div>
              <div className="flex flex-col items-center text-center">
                <Smartphone className="h-12 w-12 text-green-600 mb-4" />
                <h3 className="text-xl font-bold mb-2">Mobile Access</h3>
                <p className="text-gray-600">
                  Access your family's health information anytime, anywhere.
                </p>
              </div>
              <div className="flex flex-col items-center text-center">
                <Heart className="h-12 w-12 text-green-600 mb-4" />
                <h3 className="text-xl font-bold mb-2">Health Insights</h3>
                <p className="text-gray-600">
                  Gain valuable insights into your family's health trends.
                </p>
              </div>
            </div>
          </div>
        </section>
        <section className="w-full py-12 md:py-24 lg:py-32 bg-green-50">
          <div className="container mx-auto px-4 md:px-6">
            <div className="flex flex-col items-center space-y-4 text-center">
              <div className="space-y-2">
                <h2 className="text-3xl font-bold tracking-tighter sm:text-4xl md:text-5xl">
                  Start Guarding Your Family's Health Today
                </h2>
                <p className="mx-auto max-w-[700px] text-gray-600 md:text-xl">
                  Join thousands of families who trust Health Guardian to manage
                  their medical records. Sign up now and take control of your
                  family's health information.
                </p>
              </div>
              <div className="w-full max-w-sm space-y-2">
                <form className="flex space-x-2">
                  <input
                    className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 flex-1"
                    placeholder="Enter your email"
                    type="email"
                  />
                  <Button className="bg-green-600 hover:bg-green-700">
                    Get Started
                  </Button>
                </form>
                <p className="text-xs text-gray-500">
                  By signing up, you agree to our Terms of Service and Privacy
                  Policy.
                </p>
              </div>
            </div>
          </div>
        </section>
      </main>
      <footer className="flex flex-col gap-2 sm:flex-row py-6 w-full shrink-0 items-center px-4 md:px-6 border-t">
        <p className="text-xs text-gray-500">
          © 2023 Health Guardian. All rights reserved.
        </p>
        <nav className="sm:ml-auto flex gap-4 sm:gap-6">
          <Link className="text-xs hover:underline underline-offset-4" href="#">
            Terms of Service
          </Link>
          <Link className="text-xs hover:underline underline-offset-4" href="#">
            Privacy
          </Link>
        </nav>
      </footer>
    </div>
  );
}
