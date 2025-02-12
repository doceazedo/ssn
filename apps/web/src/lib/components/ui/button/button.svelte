<script lang="ts" module>
	import type { WithElementRef } from "bits-ui";
	import type { HTMLAnchorAttributes, HTMLButtonAttributes } from "svelte/elements";
	import { type VariantProps, tv } from "tailwind-variants";

	export const buttonVariants = tv({
		base: "ring-offset-background focus-visible:ring-ring inline-flex items-center justify-center whitespace-nowrap rounded-lg text-sm font-medium transition-all focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 aria-disabled:pointer-events-none aria-disabled:opacity-50 border border-white/15",
		variants: {
			variant: {
				default: "bg-primary text-primary-foreground hover:bg-primary/90",
				destructive: "bg-destructive text-destructive-foreground hover:bg-destructive/90",
				outline:
				"border-input bg-background hover:bg-accent hover:text-accent-foreground",
				secondary: "bg-secondary text-secondary-foreground hover:bg-secondary/80",
				ghost: "border-0 hover:bg-accent hover:text-accent-foreground",
				link: "border-0 hover:bg-accent hover:text-accent-foreground underline-offset-2 underline",
				discord: "bg-[#5865F2] text-white hover:bg-[#5865F2]/90",
			},
			size: {
				default: "h-10 px-4 py-2 gap-2",
				sm: "h-9 px-3",
				lg: "h-14 text-base px-6 gap-4 hover:-translate-y-0.5",
				icon: "h-10 w-10",
			},
		},
		defaultVariants: {
			variant: "default",
			size: "default",
		},
	});

	export type ButtonVariant = VariantProps<typeof buttonVariants>["variant"];
	export type ButtonSize = VariantProps<typeof buttonVariants>["size"];

	export type ButtonProps = WithElementRef<HTMLButtonAttributes> &
		WithElementRef<HTMLAnchorAttributes> & {
			variant?: ButtonVariant;
			size?: ButtonSize;
			loading?: boolean;
		};
</script>

<script lang="ts">
	import { cn } from "$lib/utils.js";
	import { CircleNotch } from "phosphor-svelte";

	let {
		class: className,
		variant = "default",
		size = "default",
		ref = $bindable(null),
		href = undefined,
		type = "button",
		loading = false,
		disabled,
		children,
		...restProps
	}: ButtonProps = $props();
</script>

{#if href}
	<a
		bind:this={ref}
		class={cn(buttonVariants({ variant, size }), className)}
		{href}
		{...restProps}
	>
		{@render children?.()}
	</a>
{:else}
	<button
		bind:this={ref}
		class={cn(buttonVariants({ variant, size }), className)}
		disabled={loading || disabled}
		{type}
		{...restProps}
	>
		{#if loading}
			<CircleNotch size={20} class="animate-spin" />
		{/if}
		{@render children?.()}
	</button>
{/if}
