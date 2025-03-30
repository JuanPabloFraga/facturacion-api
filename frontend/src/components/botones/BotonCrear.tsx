import React from 'react';
import { useNavigate } from 'react-router-dom';

interface BotonCrearProps {
  texto: string;
  ruta?: string; // ahora opcional, ya que podrías solo querer ejecutar una acción código JS
  onClick?: () => void;
}

const BotonCrear: React.FC<BotonCrearProps> = ({ texto, ruta, onClick }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    if (onClick) {
      onClick(); // ejecuta la función personalizada primero si existe
    } else if (ruta) {
      navigate(ruta); // navega solo si le diste ruta y no tiene función personalizada
    }
  };

  return (
    <button onClick={handleClick}>
      ➕ {texto}
    </button>
  );
};

export default BotonCrear;