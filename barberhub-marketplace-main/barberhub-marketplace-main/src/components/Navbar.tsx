import React from 'react';
import { Link } from 'react-router-dom';

const Navbar: React.FC = () => {
  const user = { role: 'BARBEIRO' }; // Replace with actual user data

  return (
    <div className="flex items-center justify-between p-4">
      {/* Rest of the component content */}

      {user?.role === "BARBEIRO" && (
        <>
          <Link
            to="/barber/profile"
            className="text-barber-600 hover:text-barber-900"
          >
            Perfil
          </Link>
          <Link
            to="/barber/edit-profile"
            className="text-barber-600 hover:text-barber-900"
          >
            Editar Perfil
          </Link>
          <Link
            to="/barber/employees"
            className="text-barber-600 hover:text-barber-900"
          >
            Profissionais
          </Link>
        </>
      )}
    </div>
  );
};

export default Navbar; 