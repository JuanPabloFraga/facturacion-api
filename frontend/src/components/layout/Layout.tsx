import React from 'react';
import Sidebar from './Sidebar';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => (
  <div className="layout" style={{ display: 'flex' }}>
    <Sidebar />
    <main style={{ padding: '20px', width: '100%' }}>
      {children}
    </main>
  </div>
);

export default Layout;