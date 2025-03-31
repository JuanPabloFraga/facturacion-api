import React from 'react';
import Sidebar from './Sidebar';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => (
  <div className="app-layout" style={{ display: 'flex' }}>
    <Sidebar  />
    <main className="app-main-content">
      {children}
    </main>
  </div>
);

export default Layout;