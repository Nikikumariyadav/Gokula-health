/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */
import { useState } from 'react';
import Dashboard from './components/Dashboard';
import VitalsTracker from './components/VitalsTracker';
import AIHealthChat from './components/AIHealthChat';
import NutritionTracker from './components/NutritionTracker';
import FitnessTracker from './components/FitnessTracker';
import Sidebar from './components/Sidebar';

export type Page = 'dashboard' | 'vitals' | 'ai-chat' | 'nutrition' | 'fitness';

export default function App() {
  const [activePage, setActivePage] = useState<Page>('dashboard');
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const renderPage = () => {
    switch (activePage) {
      case 'dashboard':   return <Dashboard onNavigate={setActivePage} />;
      case 'vitals':      return <VitalsTracker />;
      case 'ai-chat':     return <AIHealthChat />;
      case 'nutrition':   return <NutritionTracker />;
      case 'fitness':     return <FitnessTracker />;
      default:            return <Dashboard onNavigate={setActivePage} />;
    }
  };

  return (
    <div className="flex h-screen bg-[#0a0f1e] overflow-hidden">
      <Sidebar
        activePage={activePage}
        onNavigate={(p) => { setActivePage(p); setSidebarOpen(false); }}
        isOpen={sidebarOpen}
        onClose={() => setSidebarOpen(false)}
      />
      <div className="flex-1 flex flex-col overflow-hidden">
        {/* Mobile top bar */}
        <div className="lg:hidden flex items-center gap-3 px-4 py-3 border-b border-slate-800 bg-[#0d1525]">
          <button
            onClick={() => setSidebarOpen(true)}
            className="p-2 rounded-lg bg-slate-800 text-slate-300 hover:bg-slate-700 transition-colors"
          >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
            </svg>
          </button>
          <span className="font-bold text-sky-400 text-lg">Gokula Health</span>
        </div>
        <main className="flex-1 overflow-y-auto">
          {renderPage()}
        </main>
      </div>
    </div>
  );
}
