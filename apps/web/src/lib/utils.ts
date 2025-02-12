import { type ClassValue, clsx } from 'clsx';
import { toast as _toast, type Renderable, type ToastOptions } from 'svelte-french-toast';
import { twMerge } from 'tailwind-merge';

export function cn(...inputs: ClassValue[]) {
	return twMerge(clsx(inputs));
}

const DEFAULT_TOAST_OPTIONS: Partial<ToastOptions> = {
	position: 'bottom-center',
	duration: 5000
};

export const toast = {
	error: (message: Renderable, options?: ToastOptions) =>
		_toast.error(message, {
			...DEFAULT_TOAST_OPTIONS,
			className: cn('!bg-destructive !text-destructive-foreground !text-sm'),
			iconTheme: {
				primary: 'hsl(var(--destructive-foreground))',
				secondary: 'hsl(var(--destructive))'
			},
			...options
		}),
	success: (message: Renderable, options?: ToastOptions) =>
		_toast.success(message, {
			...DEFAULT_TOAST_OPTIONS,
			className: cn('!bg-emerald-500 !text-white !text-sm'),
			iconTheme: {
				primary: '#ffffff',
				secondary: '#10b981'
			},
			...options
		})
};

export const copyToClipboard = (string: string) => {
	const input = document.createElement('input');
	input.value = string;
	document.body.appendChild(input);
	input.select();
	input.setSelectionRange(0, 99999);
	navigator?.clipboard?.writeText(input.value);
	input.remove();
};
