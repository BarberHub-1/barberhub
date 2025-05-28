import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Plus, Pencil, Trash2, Mail, Phone } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import EmployeeForm from "@/components/EmployeeForm";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";

interface Employee {
  id: string;
  name: string;
  role: string;
  services: string[];
  email: string;
  phone: string;
  photo?: string;
}

const BarberEmployees = () => {
  const { toast } = useToast();
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [isFormOpen, setIsFormOpen] = useState(false);
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState<Employee | undefined>();
  const [employeeToDelete, setEmployeeToDelete] = useState<Employee | undefined>();

  const handleAddEmployee = () => {
    setSelectedEmployee(undefined);
    setIsFormOpen(true);
  };

  const handleEditEmployee = (employee: Employee) => {
    setSelectedEmployee(employee);
    setIsFormOpen(true);
  };

  const handleDeleteEmployee = (employee: Employee) => {
    setEmployeeToDelete(employee);
    setIsDeleteDialogOpen(true);
  };

  const handleSaveEmployee = (employeeData: Omit<Employee, "id">) => {
    if (selectedEmployee) {
      // Editar funcionário existente
      setEmployees(
        employees.map((emp) =>
          emp.id === selectedEmployee.id
            ? { ...employeeData, id: emp.id }
            : emp
        )
      );
      toast({
        title: "Sucesso",
        description: "Barbeiro atualizado com sucesso!",
      });
    } else {
      // Adicionar novo funcionário
      const newEmployee = {
        ...employeeData,
        id: Math.random().toString(36).substr(2, 9),
      };
      setEmployees([...employees, newEmployee]);
      toast({
        title: "Sucesso",
        description: "Barbeiro adicionado com sucesso!",
      });
    }
    setIsFormOpen(false);
    setSelectedEmployee(undefined);
  };

  const confirmDelete = () => {
    if (employeeToDelete) {
      setEmployees(employees.filter((emp) => emp.id !== employeeToDelete.id));
      toast({
        title: "Sucesso",
        description: "Barbeiro removido com sucesso!",
      });
    }
    setIsDeleteDialogOpen(false);
  };

  const handleCloseForm = () => {
    setIsFormOpen(false);
    setSelectedEmployee(undefined);
  };

  return (
    <div className="container mx-auto py-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Gerenciamento de Barbeiros</h1>
        <Button onClick={handleAddEmployee}>
          <Plus className="w-4 h-4 mr-2" />
          Adicionar Barbeiro
        </Button>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {employees.map((employee) => (
          <Card key={employee.id}>
            <CardHeader>
              <CardTitle className="flex justify-between items-center">
                {employee.name}
                <div className="flex gap-2">
                  <Button
                    variant="ghost"
                    size="icon"
                    onClick={() => handleEditEmployee(employee)}
                  >
                    <Pencil className="w-4 h-4" />
                  </Button>
                  <Button
                    variant="ghost"
                    size="icon"
                    onClick={() => handleDeleteEmployee(employee)}
                  >
                    <Trash2 className="w-4 h-4" />
                  </Button>
                </div>
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-2">
                <div className="flex items-center text-sm text-muted-foreground">
                  <Mail className="w-4 h-4 mr-2" />
                  {employee.email}
                </div>
                <div className="flex items-center text-sm text-muted-foreground">
                  <Phone className="w-4 h-4 mr-2" />
                  {employee.phone}
                </div>
                <div className="flex flex-wrap gap-2 mt-4">
                  {employee.services.map((service) => (
                    <span
                      key={service}
                      className="px-2 py-1 bg-secondary text-secondary-foreground rounded-md text-sm"
                    >
                      {service}
                    </span>
                  ))}
                </div>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      <Dialog open={isFormOpen} onOpenChange={handleCloseForm}>
        <DialogContent className="max-w-2xl">
          <DialogHeader>
            <DialogTitle>
              {selectedEmployee ? "Editar Barbeiro" : "Adicionar Novo Barbeiro"}
            </DialogTitle>
          </DialogHeader>
          <EmployeeForm
            employee={selectedEmployee}
            onSave={handleSaveEmployee}
            onCancel={handleCloseForm}
          />
        </DialogContent>
      </Dialog>

      <AlertDialog open={isDeleteDialogOpen} onOpenChange={setIsDeleteDialogOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar exclusão</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza que deseja remover este barbeiro? Esta ação não pode
              ser desfeita.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction onClick={confirmDelete}>
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
};

export default BarberEmployees; 