import { useTheme } from "next-themes"
import { Toaster as Sonner } from "sonner"
import { toast } from "sonner"

type ToasterProps = React.ComponentProps<typeof Sonner>

const Toaster = ({ ...props }: ToasterProps) => {
  const { theme = "system" } = useTheme()

  return (
    <Sonner
      theme={theme as ToasterProps["theme"]}
      className="toaster group"
      toastOptions={{
        classNames: {
          toast:
            "group toast group-[.toaster]:bg-background group-[.toaster]:text-foreground group-[.toaster]:border-border group-[.toaster]:shadow-lg",
          description: "group-[.toast]:text-muted-foreground",
          actionButton:
            "group-[.toast]:bg-primary group-[.toast]:text-primary-foreground",
          cancelButton:
            "group-[.toast]:bg-muted group-[.toast]:text-muted-foreground",
        },
      }}
      {...props}
    />
  )
}

// Componente Sonner usando React.forwardRef
// Integra com o componente Sonner do Radix UI
// Permite customização através de classes adicionais
const Sonner = () => {
  return (
    <div className="fixed bottom-0 right-0 z-50">
      <div className="flex flex-col gap-2 p-4">
        {toast.message}
      </div>
    </div>
  )
}

export { Toaster, Sonner }
