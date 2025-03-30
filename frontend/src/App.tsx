// App.tsx (main.tsx o index.tsx generalmente)

import React from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import MiRutasApp from './routes/MiRutasApp'; // tus rutas aquí


const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <MiRutasApp/>
  </QueryClientProvider>
);

export default App;